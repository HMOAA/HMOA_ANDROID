package com.hmoa.feature_community.ViewModel

import android.util.Log
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
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityDescViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val communityCommentRepository: CommunityCommentRepository
) : ViewModel() {

    private val _isOpenBottomOptions = MutableStateFlow(false)
    val isOpenBottomOptions get() = _isOpenBottomOptions.asStateFlow()

    private val _errState = MutableStateFlow("")
    val errState get() = _errState.asStateFlow()

    private val _id = MutableStateFlow(-1)
    val id get() = _id.asStateFlow()

    private val _isLiked = MutableStateFlow(false)
    val isLiked get() = _isLiked.asStateFlow()

    private val _page = MutableStateFlow(0)
    val page get() = _page.asStateFlow()

    private val _profile = MutableStateFlow<String?>(null)
    val profile get() = _profile.asStateFlow()

    private val _flag = MutableStateFlow(false)
    val flag get() = _flag.asStateFlow()

    private var _communities = MutableStateFlow<PagingData<CommunityCommentWithLikedResponseDto>?>(null)

    //community flow
    private val communityFlow = combine(_flag, _id){ flag, id ->
        fetchLike(isLiked.value)
        flow{
            val result= communityRepository.getCommunity(id)
            if (result.exception is Exception) {
                throw result.exception!!
            } else {
                _isLiked.update{result.data!!.liked}
                emit(result.data!!)
            }
            _flag.update{false}
        }
    }.flatMapLatest{
        flow -> flow.asResult()
    }

    //ui state
    val uiState : StateFlow<CommunityDescUiState> = combine(
        communityFlow,
        _communities
    ){ communityResult, commentsResult ->
        when {
            communityResult is Result.Error-> {
                CommunityDescUiState.Error
            }
            communityResult is Result.Loading -> CommunityDescUiState.Loading
            else -> {
                Log.d("TAG TEST", "comment : ${commentsResult}")
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

    //신고하기
    fun reportCommunity() {
        /** 신고하기 repository 추가되어서 이거 추가해야 함 */
    }

    //삭제
    fun delCommunity() {
        viewModelScope.launch {
            try {
                communityRepository.deleteCommunity(id.value)
            } catch (e: Exception) {
                _errState.update { e.message.toString() }
            }
        }
    }

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

    //id 설정
    fun setId(id: Int) {
        _id.update { id }
    }

    //좋아요 local update
    fun updateLike(){
        _flag.update{true}
    }

    fun commentPagingSource() : Flow<PagingData<CommunityCommentWithLikedResponseDto>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getCommentPaging(id = id.value)
        }
    ).flow.cachedIn(viewModelScope)

    fun updateCommentLike(
        id : Int,
        liked : Boolean
    ) {
        viewModelScope.launch{
            if (liked) {
                val result = communityCommentRepository.putCommunityCommentLiked(id)
                if (result.exception is Exception){
                    _errState.update{ result.exception.toString()}
                    return@launch
                }
            } else {
                val result = communityCommentRepository.deleteCommunityCommentLiked(id)
                if (result.exception is Exception){
                    _errState.update{ result.exception.toString() }
                    return@launch
                }
            }

        }
    }

    //좋아요 remote update
    private suspend fun fetchLike(liked : Boolean){
        viewModelScope.launch{
            if (flag.value) {
                if (liked) {
                    val result = communityRepository.deleteCommunityLike(id.value)
                    if (result.exception is Exception) {
                        _errState.update{result.exception.toString()}
                        _flag.update{false}
                        return@launch
                    }
                } else {
                    val result = communityRepository.putCommunityLike(id.value)
                    if (result.exception is Exception) {
                        _errState.update{result.exception.toString()}
                        _flag.update{false}
                        return@launch
                    }
                }
                _isLiked.update{ !liked }
            }
        }
    }

    private fun getCommentPaging(id : Int) = CommunityCommentPagingSource(
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