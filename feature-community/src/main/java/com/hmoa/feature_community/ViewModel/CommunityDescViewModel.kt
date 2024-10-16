package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityDescViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val communityCommentRepository: CommunityCommentRepository,
    private val reportRepository: ReportRepository,
) : ViewModel() {
    //community id
    private val _id = MutableStateFlow(-1)
    val id get() = _id.asStateFlow()

    //like 여부
    private val _isLiked = MutableStateFlow(false)
    val isLiked get() = _isLiked.asStateFlow()
    private val _reportState = MutableStateFlow<Boolean>(false)
    val reportState get() = _reportState.asStateFlow()

    private val flag = MutableStateFlow<Boolean>(false)

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
    //ui state
    val uiState: StateFlow<CommunityDescUiState> = combine(errorUiState, _id) { errState, id ->
        if (errState is ErrorUiState.ErrorData &&
            (errState.expiredTokenError || errState.wrongTypeTokenError || errState.unknownError || errState.generalError.first)){
            throw Exception("")
        }
        val result = communityRepository.getCommunity(id)
        result
    }.asResult().map{ communityResult ->
        when(communityResult) {
            is Result.Loading -> CommunityDescUiState.Loading
            is Result.Success -> CommunityDescUiState.CommunityDesc((communityResult).data.data!!)
            is Result.Error -> {
                if(communityResult.exception.message != "") {
                    when (communityResult.exception.message) {
                        ErrorMessageType.EXPIRED_TOKEN.message -> expiredTokenErrorState.update { true }
                        ErrorMessageType.WRONG_TYPE_TOKEN.message -> wrongTypeTokenErrorState.update { true }
                        ErrorMessageType.UNKNOWN_ERROR.message -> unLoginedErrorState.update { true }
                        else -> generalErrorState.update {Pair(true,communityResult.exception.message)}
                    }
                }
                CommunityDescUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunityDescUiState.Loading
    )

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
        viewModelScope.launch {
            val requestDto = TargetRequestDto(targetId = id.value.toString())
            val result = reportRepository.reportCommunity(requestDto)
            if(result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update{true}}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update{true}}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update{true}}
                    else -> {generalErrorState.update{Pair(true, result.errorMessage!!.message)}}
                }
            }
        }
    }
    //커뮤니티 좋아요
    fun updateLike(liked: Boolean) {
        viewModelScope.launch{
            val result = if(liked) communityRepository.deleteCommunityLike(id.value) else communityRepository.putCommunityLike(id.value)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update{true}}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update{true}}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update{true}}
                    else -> {generalErrorState.update{Pair(true, result.errorMessage!!.message)}}
                }
            }
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
        viewModelScope.launch {
            val requestDto = CommunityCommentDefaultRequestDto(content = comment)
            val result = communityCommentRepository.postCommunityComment(communityId = id.value,dto = requestDto)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update{true}}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update{true}}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update{true}}
                    else -> {generalErrorState.update{Pair(true, result.errorMessage!!.message)}}
                }
                return@launch
            }
            flag.update{!flag.value}
        }
    }
    //댓글 삭제
    fun delComment(id: Int) {
        viewModelScope.launch {
            val result = communityCommentRepository.deleteCommunityComment(id)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update{true}}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update{true}}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update{true}}
                    else -> {generalErrorState.update{Pair(true, result.errorMessage!!.message)}}
                }
                return@launch
            }
            flag.update{!flag.value}
        }
    }
    //댓글 신고하기
    fun reportComment(id: Int) {
        viewModelScope.launch {
            val requestDto = TargetRequestDto(id.toString())
            val result = reportRepository.reportCommunityComment(requestDto)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update{true}}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update{true}}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update{true}}
                    else -> {generalErrorState.update{Pair(true, result.errorMessage!!.message)}}
                }
            }
        }
    }
    //댓글 좋아요
    fun updateCommentLike(
        id: Int,
        liked: Boolean
    ) {
        viewModelScope.launch {
            val result = if(liked) communityCommentRepository.deleteCommunityCommentLiked(id) else communityCommentRepository.putCommunityCommentLiked(id)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update{true}}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update{true}}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update{true}}
                    else -> {generalErrorState.update{Pair(true, result.errorMessage!!.message)}}
                }
            }
        }
    }
    //id 설정
    fun setId(id: Int?) {
        if (id == null) {
            generalErrorState.update { Pair(true, "해당 글을 찾을 수 없습니다.") }
            return
        } else if (id == -1) {
            generalErrorState.update { Pair(true, "이미 삭제된 게시글 입니다.") }
            return
        }
        _id.update { id }
    }
    private fun getCommentPaging(id: Int) : CommunityCommentPagingSource {
        return CommunityCommentPagingSource(
            communityCommentRepository = communityCommentRepository,
            id = id
        )
    }
}

sealed interface CommunityDescUiState {
    data object Loading : CommunityDescUiState
    data class CommunityDesc(
        val community: CommunityDefaultResponseDto,
    ) : CommunityDescUiState {
        val photoList = community.communityPhotos.map {it.photoUrl}
    }
    data object Error : CommunityDescUiState
}