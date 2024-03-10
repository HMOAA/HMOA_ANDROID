package com.hmoa.feature_userinfo.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetMyCommentByPerfumeUseCase
import com.hmoa.core_domain.usecase.GetMyCommentByPostUseCase
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repository : MemberRepository,
    private val commentUseCaseByPerfume : GetMyCommentByPerfumeUseCase,
    private val commentUseCaseByPost : GetMyCommentByPostUseCase
): ViewModel() {

    //댓글 종류 (perfume / post)
    private var commentType = MutableStateFlow("Perfume")

    private val _comments = MutableStateFlow<List<CommunityCommentDefaultResponseDto>>(emptyList())
    val comments get() = _comments

    //페이징
    private var page by mutableIntStateOf(0)

    val uiState : StateFlow<CommentUiState> = combine(
        commentType,
        comments,
        CommentUiState::Comments,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommentUiState.Loading
    )

    //page 더하기
    fun addPage(){
        page += 1
    }

    //type update
    fun updateType(newType : String) {
        commentType.value = newType
        updateComments()
    }

    private fun updateComments(){
        viewModelScope.launch{
            val result = if (commentType.value == "Perfume") {
                commentUseCaseByPerfume.invoke(page)
            } else {
                commentUseCaseByPost.invoke(page)
            }

            result.collect{
                _comments.value = it
            }
        }
    }

}

sealed interface CommentUiState{
    data object Loading : CommentUiState

    data class Comments(
        val type : String,
        val comments : List<CommunityCommentDefaultResponseDto>
    ) : CommentUiState

    data object Empty : CommentUiState
}