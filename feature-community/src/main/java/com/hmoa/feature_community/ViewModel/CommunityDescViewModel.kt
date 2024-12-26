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
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.feature_community.CommunityCommentPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CommunityDescViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val communityCommentRepository: CommunityCommentRepository,
    private val reportRepository: ReportRepository,
) : ViewModel() {
    //community id
    private val id = MutableStateFlow(-1)

    //like 여부
    private val _isLiked = MutableStateFlow(false)
    val isLiked get() = _isLiked.asStateFlow()

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
    val uiState: StateFlow<CommunityDescUiState> = combine(errorUiState, id) { errState, id ->
        if (errState is ErrorUiState.ErrorData && errState.isValidate()){ throw Exception("") }
        val response = communityRepository.getCommunity(id)
        if (response.errorMessage != null) throw Exception(response.errorMessage!!.message)
        response.data!!
    }.asResult().map{ result ->
        when(result) {
            is Result.Loading -> CommunityDescUiState.Loading
            is Result.Success -> {
                val data = result.data
                val communityPhotos = data.communityPhotos.map{it.photoUrl}
                CommunityDescUiState.CommunityDesc(
                    author = data.author,
                    category = data.category,
                    communityPhotos = communityPhotos.toImmutableList(),
                    content = data.content,
                    heartCount = data.heartCount,
                    imagesCount = data.imagesCount,
                    liked = data.liked,
                    myProfileImgUrl = data.myProfileImgUrl,
                    profileImgUrl = data.profileImgUrl,
                    time = data.time,
                    title = data.title,
                    writed = data.writed
                )
            }
            is Result.Error -> {
                if(result.exception.message != "") {
                    handleErrorType(
                        error = result.exception,
                        onExpiredTokenError = {expiredTokenErrorState.update{true}},
                        onWrongTypeTokenError = {wrongTypeTokenErrorState.update { true }},
                        onUnknownError = {unLoginedErrorState.update { true }},
                        onGeneralError = {generalErrorState.update {Pair(true,result.exception.message)}}
                    )
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
    fun delCommunity(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = communityRepository.deleteCommunity(id.value)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update { true }}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update { true }}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update { true }}
                    else -> {generalErrorState.update { Pair(true, result.errorMessage!!.message) }}
                }
                return@launch
            }
            withContext(Dispatchers.Main){ onSuccess() }
        }
    }
    //커뮤니티 신고하기
    fun reportCommunity(onSuccess: () -> Unit) {
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
                return@launch
            }
            withContext(Dispatchers.Main){
                onSuccess()
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
        }
    }
    //댓글 삭제
    fun delComment(id: Int, onSuccess: () -> Unit) {
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
            withContext(Dispatchers.Main){ onSuccess() }
        }
    }
    //댓글 신고하기
    fun reportComment(id: Int, onSuccess: () -> Unit) {
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
                return@launch
            }
            withContext(Dispatchers.Main){ onSuccess() }
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
    fun setId(newId: Int?) {
        if (newId == null) {
            generalErrorState.update { Pair(true, "해당 글을 찾을 수 없습니다.") }
            return
        }
        id.update { newId }
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
        val author: String,
        val category: String,
        val communityPhotos: ImmutableList<String>,
        val content: String,
        val heartCount: Int,
        val imagesCount: Int,
        val liked: Boolean,
        val myProfileImgUrl: String?,
        val profileImgUrl: String?,
        val time: String,
        val title: String,
        val writed: Boolean,
    ) : CommunityDescUiState
    data object Error : CommunityDescUiState
}