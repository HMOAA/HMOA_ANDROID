package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repository: MemberRepository
) : ViewModel() {

    val uiState: StateFlow<CommentUiState> = flow {
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

sealed interface CommentUiState {
    data object Loading : CommentUiState

    data class Comments(
        val comments: List<CommunityCommentDefaultResponseDto>
    ) : CommentUiState

    data object Empty : CommentUiState
}