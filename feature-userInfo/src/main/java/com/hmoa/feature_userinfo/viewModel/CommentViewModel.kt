package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_model.response.CommunityCommentByMemberResponseDto
import com.hmoa.core_repository.Member.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repository : MemberRepository
): ViewModel() {

    val uiState : StateFlow<CommentUiState> = flow {
        val comments = repository.getCommunityComments((0))
        emit(comments)
    }
        .map(CommentUiState::Comments)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3_000),
            initialValue = CommentUiState.Loading
        )

}

sealed interface CommentUiState{
    data object Loading : CommentUiState

    data class Comments(
        val comments : List<CommunityCommentByMemberResponseDto>
    ) : CommentUiState

    data object Empty : CommentUiState
}