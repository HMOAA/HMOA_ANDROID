package com.example.feature_userinfo.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetMyCommentUseCase
import com.hmoa.core_model.response.CommunityCommentByMemberResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repository : MemberRepository,
    commentUseCase : GetMyCommentUseCase,
): ViewModel() {

    //댓글 종류 (perfume / post)
    private var commentType : StateFlow<String> = MutableStateFlow("Perfume")

    //페이징
    private var page by mutableStateOf(0)

    val uiState : StateFlow<CommentUiState> = combine(
        commentType,
        commentUseCase.invoke(page),
        CommentUiState::Comments,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommentUiState.Loading
    )

    
}

sealed interface CommentUiState{
    data object Loading : CommentUiState

    data class Comments(
        val type : String,
        val comments : List<CommunityCommentByMemberResponseDto>
    ) : CommentUiState

    data object Empty : CommentUiState
}