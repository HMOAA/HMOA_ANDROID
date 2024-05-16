package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.feature_community.CommunityCommentPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityDescViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val communityCommentRepository: CommunityCommentRepository,
    private val reportRepository: ReportRepository,
    private val loginRepository : LoginRepository,
) : ViewModel() {
    private val authToken = MutableStateFlow<String?>(null)
    //바텀 다이얼로그 state
    private val _isOpenBottomOptions = MutableStateFlow(false)
    val isOpenBottomOptions get() = _isOpenBottomOptions.asStateFlow()
    //community id
    private val _id = MutableStateFlow(-1)
    val id get() = _id.asStateFlow()

    //like 여부
    private val _isLiked = MutableStateFlow(false)
    val isLiked get() = _isLiked.asStateFlow()

    private val _flag = MutableStateFlow(false)

    private var _communities = MutableStateFlow<PagingData<CommunityCommentWithLikedResponseDto>?>(null)

    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorUiState: StateFlow<ErrorUiState> = combine(
        expiredTokenErrorState,
        wrongTypeTokenErrorState,
        unLoginedErrorState,
        generalErrorState
    ) { expiredTokenError, wrongTypeTokenError, unknownError, generalError ->
        ErrorUiState.ErrorData(
            expiredTokenError = expiredTokenError,
            wrongTypeTokenError = wrongTypeTokenError,
            unknownError = unknownError,
            generalError = generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )
    init{
        getAuthToken()
    }
    //community flow
    private val communityFlow = combine(_flag, _id) { flag, id ->
        fetchLike(isLiked.value)
        flow {
            val result = communityRepository.getCommunity(id)
            if (result.errorMessage != null) {
                throw Exception(result.errorMessage!!.message)
            } else {
                _isLiked.update { result.data!!.liked }
                emit(result.data!!)
            }
            _flag.update { false }
        }
    }.flatMapLatest { flow ->
        flow.asResult()
    }
    //ui state
    val uiState: StateFlow<CommunityDescUiState> = combine(
        communityFlow,
        _communities
    ) { communityResult, commentsResult ->
        when {
            communityResult is Result.Error -> CommunityDescUiState.Error
            communityResult is Result.Loading -> CommunityDescUiState.Loading
            else -> CommunityDescUiState.CommunityDesc((communityResult as Result.Success).data,commentsResult)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunityDescUiState.Loading
    )

    // get token
    private fun getAuthToken() {
        viewModelScope.launch {
            loginRepository.getAuthToken().onEmpty { }.collectLatest {
                authToken.value = it
            }
        }
    }
    //token 보유 여부 return
    fun hasToken() : Boolean = authToken.value != null
    //bottom option 상태 업데이트
    fun updateBottomOptionsState(state: Boolean) {
        _isOpenBottomOptions.update { state }
    }
    //커뮤니티 삭제
    fun delCommunity() {
        viewModelScope.launch {
            try {
                communityRepository.deleteCommunity(id.value)
            } catch (e: Exception) {
                generalErrorState.update { Pair(true, e.message) }
            }
        }
    }
    //커뮤니티 신고하기
    fun reportCommunity() {
        if(authToken.value == null){
            unLoginedErrorState.update{ true }
        } else {
            viewModelScope.launch {
                try {
                    val requestDto = TargetRequestDto(targetId = id.value.toString())
                    reportRepository.reportCommunity(requestDto)
                } catch (e: Exception) {
                    generalErrorState.update{ Pair(true, e.message) }
                }
            }
        }
    }
    //커뮤니티 좋아요
    fun updateLike() {
        if(authToken.value == null){
            unLoginedErrorState.update{ true }
        } else {
            _flag.update { true }
        }
    }
    //댓글 Paging
    fun commentPagingSource(): Flow<PagingData<CommunityCommentWithLikedResponseDto>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getCommentPaging(id = id.value)
        }
    ).flow.cachedIn(viewModelScope)
    //댓글 작성
    fun postComment(comment: String) {
        if(authToken.value == null){
            unLoginedErrorState.update{ true }
        } else {
            viewModelScope.launch {
                val requestDto = CommunityCommentDefaultRequestDto(content = comment)
                try {
                    communityCommentRepository.postCommunityComment(communityId = id.value,dto = requestDto)
                } catch (e: Exception) {
                    generalErrorState.update{ Pair(true, e.message) }
                }
            }
        }
    }
    //댓글 삭제
    fun delComment(id: Int?) {
        if(authToken.value == null){
            unLoginedErrorState.update{ true }
        } else {
            viewModelScope.launch {
                if (id == null) {
                    generalErrorState.update{ Pair(true, "해당 댓글을 찾을 수 없습니다.")}
                    return@launch
                }
                val result = communityCommentRepository.deleteCommunityComment(id)
                if (result.errorMessage is ErrorMessage) {
                    generalErrorState.update { Pair(true, result.errorMessage?.message) }
                    return@launch
                }
            }
        }
    }
    //댓글 신고하기
    fun reportComment(id: Int) {
        if(authToken.value == null){
            unLoginedErrorState.update{ true }
        } else {
            viewModelScope.launch {
                val requestDto = TargetRequestDto(id.toString())
                try {
                    reportRepository.reportCommunityComment(requestDto)
                } catch (e: Exception) {
                    generalErrorState.update { Pair(true, e.message) }
                }
            }
        }
    }
    //댓글 좋아요
    fun updateCommentLike(
        id: Int,
        liked: Boolean
    ) {
        if(authToken.value == null){
            unLoginedErrorState.update{ true }
        } else {
            viewModelScope.launch {
                if (liked) {
                    val result = communityCommentRepository.putCommunityCommentLiked(id)
                    if (result.errorMessage != null) {
                        generalErrorState.update { Pair(true, result.errorMessage?.message) }
                        return@launch
                    }
                } else {
                    val result = communityCommentRepository.deleteCommunityCommentLiked(id)
                    if (result.errorMessage != null) {
                        generalErrorState.update { Pair(true, result.errorMessage?.message) }
                        return@launch
                    }
                }
            }
        }
    }
    //id 설정
    fun setId(id: Int?) {
        if (id == null) {
            generalErrorState.update { Pair(true, "해당 글을 찾을 수 없습니다.") }
            return
        } else if (
            id == -1) {
            generalErrorState.update { Pair(true, "이미 삭제된 게시글 입니다.") }
            return
        }
        _id.update { id }
    }
    //좋아요 remote update
    private suspend fun fetchLike(liked: Boolean) {
        viewModelScope.launch {
            if (_flag.value) {
                if (liked) {
                    val result = communityRepository.deleteCommunityLike(id.value)
                    if (result.errorMessage != null) {
                        generalErrorState.update{ Pair(true, result.errorMessage?.message) }
                        _flag.update { false }
                        return@launch
                    }
                } else {
                    val result = communityRepository.putCommunityLike(id.value)
                    if (result.errorMessage != null) {
                        generalErrorState.update{ Pair(true, result.errorMessage?.message) }
                        _flag.update { false }
                        return@launch
                    }
                }
                _isLiked.update { !liked }
            }
        }
    }
    private fun getCommentPaging(id: Int) = CommunityCommentPagingSource(
        communityCommentRepository = communityCommentRepository,
        id = id
    )
}

sealed interface CommunityDescUiState {
    data object Loading : CommunityDescUiState
    data class CommunityDesc(
        val community: CommunityDefaultResponseDto,
        val comments: PagingData<CommunityCommentWithLikedResponseDto>?
    ) : CommunityDescUiState {
        val photoList = community.communityPhotos.map {
            it.photoUrl
        }
    }

    data object Error : CommunityDescUiState
}