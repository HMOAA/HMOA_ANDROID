package com.hmoa.feature_perfume.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificCommentViewmodel @Inject constructor(
    private val perfumeCommentRepository: PerfumeCommentRepository
) : ViewModel() {
    private val perfumeCommentState = MutableStateFlow<PerfumeCommentResponseDto?>(null)
    private val likePerfumeCommentState = MutableStateFlow<Boolean>(false)

    val uiState: StateFlow<SpecificCommentUiState> =
        combine(perfumeCommentState, likePerfumeCommentState) { perfumeComment, likePerfume ->
            SpecificCommentUiState.CommentData(comment = perfumeComment, isLikeComment = likePerfume)

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SpecificCommentUiState.Loading
        )

    fun initializePerfumeComment(commentId: Int) {
        viewModelScope.launch {
            perfumeCommentState.update { perfumeCommentRepository.getPerfumeComment(commentId) }
        }
    }

    fun onChangeLikePerfumceCommnet() {

    }

    sealed interface SpecificCommentUiState {
        data object Loading : SpecificCommentUiState
        data class CommentData(
            val comment: PerfumeCommentResponseDto?,
            val isLikeComment: Boolean
        ) : SpecificCommentUiState

        data object Error : SpecificCommentUiState
    }
}