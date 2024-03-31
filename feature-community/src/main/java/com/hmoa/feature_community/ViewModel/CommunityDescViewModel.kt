package com.hmoa.feature_community.ViewModel

import ResultResponse
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.s
import javax.inject.Inject

@HiltViewModel
class CommunityDescViewModel @Inject constructor(
    private val communityRepository : CommunityRepository,
    private val communityCommentRepository : CommunityCommentRepository,
    getUserInfo : GetMyUserInfoUseCase,
) : ViewModel() {

    private val _isOpenBottomOptions = MutableStateFlow(false)
    val isOpenBottomOptions get() = _isOpenBottomOptions.asStateFlow()

    private val _errState = MutableStateFlow("")
    val errState get() = _errState.asStateFlow()

    private val _id = MutableStateFlow(-1)
    val id get() = _id.asStateFlow()

    private val _isLiked = MutableStateFlow<Boolean>(false)
    val isLiked get() = _isLiked.asStateFlow()

    private val _page = MutableStateFlow(0)
    val page get() = _page.asStateFlow()

    private val _profile = MutableStateFlow<String?>(null)
    val profile get() = _profile.asStateFlow()

    private val _flag = MutableStateFlow(false)
    val flag get() = _flag.asStateFlow()

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

    //comment flow
    private val commentFlow = flow{
        val result = communityCommentRepository.getCommunityComments(id.value, page.value)
        if (result.exception is Exception) {
            throw result.exception!!
        } else {
            emit(result.data!!)
        }
    }.asResult()

    //ui state
    val uiState : StateFlow<CommunityDescUiState> = combine(
        communityFlow,
        commentFlow
    ){ communityResult, commentsResult ->
        when {
            communityResult is Result.Error || commentsResult is Result.Error -> CommunityDescUiState.Error
            communityResult is Result.Loading || commentsResult is Result.Loading -> CommunityDescUiState.Loading
            else -> {
                Log.d("TAG TEST", "community : ${communityResult}")
                CommunityDescUiState.CommunityDesc(
                    (communityResult as Result.Success).data,
                    (commentsResult as Result.Success).data,
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunityDescUiState.Loading
    )

    //bottom option 상태 업데이트
    fun updateBottomOptionsState(state : Boolean){
        _isOpenBottomOptions.update{ state }
    }

    //신고하기
    fun reportCommunity(){
        /** 신고하기 repository 추가되어서 이거 추가해야 함 */
    }

    //삭제
    fun delCommunity(){
        viewModelScope.launch{
            try{
                communityRepository.deleteCommunity(id.value)
            } catch (e : Exception) {
                _errState.update{ e.message.toString() }
            }
        }
    }

    //댓글 작성
    fun postComment(comment : String){
        viewModelScope.launch{
            val requestDto = CommunityCommentDefaultRequestDto(
                content = comment
            )

            try {
                communityCommentRepository.postCommunityComment(
                    communityId = id.value,
                    dto = requestDto
                )
            } catch (e : Exception) {
                _errState.update{ e.message.toString() }
            }
        }
    }

    //id 설정
    fun setId(id: Int) {
        _id.update{ id }
    }

    //좋아요 local update
    fun updateLike(){
        Log.d("TAG TEST", "1. update like : ${isLiked.value}")
        _flag.update{true}
        Log.d("TAG TEST", "2. flag update : ${flag.value}")
    }

    //좋아요 remote update
    private suspend fun fetchLike(liked : Boolean){
        viewModelScope.launch{
            if (flag.value) {
                if (liked) {
                    communityRepository.deleteCommunityLike(id.value)
                } else {
                    communityRepository.putCommunityLike(id.value)
                }
                _isLiked.update{ !liked }
            }
        }
    }

    fun postInfo(){
        viewModelScope.launch{
            communityCommentRepository.putCommunityCommentLiked(id.value)
            communityRepository.putCommunityLike(id.value)
        }
    }
}

sealed interface CommunityDescUiState{
    data object Loading : CommunityDescUiState
    data class CommunityDesc(
        val community : CommunityDefaultResponseDto,
        val comments : CommunityCommentAllResponseDto
    ) : CommunityDescUiState {
        val photoList = community.communityPhotos.map{
            it.photoUrl
        }
    }
    data object Error : CommunityDescUiState
}

sealed interface UserInfoState{
    data object Loading : UserInfoState
    data class User(
        val profile : String,
    ) : UserInfoState
    data object Error : UserInfoState
}