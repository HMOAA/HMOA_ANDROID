package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.feature_community.CommunityCommentPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityDescViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val communityCommentRepository: CommunityCommentRepository,
    private val reportRepository: ReportRepository,
) : ViewModel() {

    //바텀 다이얼로그 state
    private val _isOpenBottomOptions = MutableStateFlow(false)
    val isOpenBottomOptions get() = _isOpenBottomOptions.asStateFlow()

    //error state
    private val _errState = MutableStateFlow("")
    val errState get() = _errState.asStateFlow()

    //community id
    private val _id = MutableStateFlow(-1)
    val id get() = _id.asStateFlow()

    //like 여부
    private val _isLiked = MutableStateFlow(false)
    val isLiked get() = _isLiked.asStateFlow()

    private val _flag = MutableStateFlow(false)

    private var _communities = MutableStateFlow<PagingData<CommunityCommentWithLikedResponseDto>?>(null)

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
            communityResult is Result.Error -> {
                CommunityDescUiState.Error
            }

            communityResult is Result.Loading -> CommunityDescUiState.Loading
            else -> {
                CommunityDescUiState.CommunityDesc(
                    (communityResult as Result.Success).data,
                    commentsResult,
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunityDescUiState.Loading
    )

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
                _errState.update { e.message.toString() }
            }
        }
    }

    //커뮤니티 신고하기
    fun reportCommunity() {
        viewModelScope.launch {
            try {
                val requestDto = TargetRequestDto(targetId = id.value.toString())
                reportRepository.reportCommunity(requestDto)
            } catch (e: Exception) {
                _errState.update { e.message.toString() }
            }
        }
    }

    //커뮤니티 좋아요
    fun updateLike() {
        _flag.update { true }
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
        viewModelScope.launch {
            val requestDto = CommunityCommentDefaultRequestDto(
                content = comment
            )
            try {
                communityCommentRepository.postCommunityComment(
                    communityId = id.value,
                    dto = requestDto
                )
            } catch (e: Exception) {
                _errState.update {
                    e.message.toString()
                }
            }
        }
    }

    //댓글 삭제
    fun delComment(id: Int?) {
        viewModelScope.launch {
            if (id == null) {
                _errState.update { "댓글 인식 오류" }
                return@launch
            }
            val result = communityCommentRepository.deleteCommunityComment(id)
            if (result.errorMessage != null) {
                _errState.update { result.errorMessage!!.message }
                return@launch
            }
        }
    }

    //댓글 신고하기
    fun reportComment(id: Int) {
        viewModelScope.launch {
            val requestDto = TargetRequestDto(id.toString())
            try {
                reportRepository.reportCommunityComment(requestDto)
            } catch (e: Exception) {
                _errState.update { e.message.toString() }
            }
        }
    }

    //댓글 좋아요
    fun updateCommentLike(
        id: Int,
        liked: Boolean
    ) {
        viewModelScope.launch {
            if (liked) {
                val result = communityCommentRepository.putCommunityCommentLiked(id)
                if (result.errorMessage != null) {
                    _errState.update { result.errorMessage!!.message }
                    return@launch
                }
            } else {
                val result = communityCommentRepository.deleteCommunityCommentLiked(id)
                if (result.errorMessage != null) {
                    _errState.update { result.errorMessage!!.message }
                    return@launch
                }
            }
        }
    }

    //id 설정
    fun setId(id: Int?) {
        if (id == null) {
            _errState.update { "앱 오류" }
            return
        } else if (
            id == -1) {
            _errState.update { "이미 삭제된 게시글 입니다." }
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
                        _errState.update { result.errorMessage!!.message }
                        _flag.update { false }
                        return@launch
                    }
                } else {
                    val result = communityRepository.putCommunityLike(id.value)
                    if (result.errorMessage != null) {
                        _errState.update { result.errorMessage!!.message }
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