package com.hmoa.feature_community.ViewModel

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val _isLiked = MutableStateFlow(false)
    val isLiked get() = _isLiked.asStateFlow()

    private val _isWritten = MutableStateFlow(false)
    val isWritten = _isWritten.asStateFlow()

    private val _page = MutableStateFlow(0)
    val page get() = _page.asStateFlow()

    private val _profile = MutableStateFlow<String?>(null)
    val profile get() = _profile.asStateFlow()

    init{
        viewModelScope.launch{
            Log.d("TAG TEST", "view model init")
            getUserInfo().asResult()
                .map{ result ->
                    when(result) {
                        Result.Loading -> _profile.update{null}
                        is Result.Success -> {
                            _profile.update { result.data.profile }
                        }
                        is Result.Error -> _errState.update{ result.exception.toString() }
                    }
                }
        }
    }

    val uiState : StateFlow<CommunityDescUiState> = combine(
        flow{
            val result = communityRepository.getCommunity(id.value)
            if (result.data == null) {
                _errState.update { "${result.errorCode} : ${result.errorMessage}"}
                return@flow
            }
            emit(result.data!!)
        }.asResult(),
        flow{
            val result = communityCommentRepository.getCommunityComments(id.value, page.value)
            if (result.data == null) {
                _errState.update { "${result.errorCode} : ${result.errorMessage}"}
                return@flow
            }
            emit(result.data!!)
        }.asResult()
    ){ communityResult, commentsResult ->
        when {
            communityResult is Result.Error || commentsResult is Result.Error -> CommunityDescUiState.Error
            communityResult is Result.Loading || commentsResult is Result.Loading -> CommunityDescUiState.Loading
            else -> {
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