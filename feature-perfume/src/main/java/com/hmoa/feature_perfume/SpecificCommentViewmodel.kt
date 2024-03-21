package com.hmoa.feature_perfume

import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class SpecificCommentViewmodel @Inject constructor(
    private val perfumeCommentRepository: PerfumeCommentRepository
) {

    sealed interface SpecificCommentUiState {
        data object Loading : SpecificCommentUiState
        data class CommentData(
            val comment: PerfumeCommentResponseDto?
        ) : SpecificCommentUiState

        data object Error : SpecificCommentUiState
    }
}