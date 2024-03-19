package com.example.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_domain.usecase.GetCommunityComment
import com.hmoa.core_domain.usecase.GetCommunityDescription
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CommunityDescViewModel @Inject constructor(
    private val reportRepository :
    communityUseCase : GetCommunityDescription,
    commentUseCase : GetCommunityComment
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

    val communityUiState : StateFlow<CommunityDescUiState> = combine(
        communityUseCase(id.value)
            .asResult(),
        commentUseCase(id.value, page.value)
            .asResult()
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

    }

    //삭제
    fun delCommunity(){

    }
}

sealed interface CommunityDescUiState{
    data object Loading : CommunityDescUiState
    data class CommunityDesc(
        val community : CommunityDefaultResponseDto,
        val comments : CommunityCommentAllResponseDto
    ) : CommunityDescUiState
    data object Error : CommunityDescUiState
}