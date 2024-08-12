package com.hyangmoa.feature_perfume.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyangmoa.core_common.Result
import com.hyangmoa.core_common.asResult
import com.hyangmoa.core_domain.repository.PerfumeCommentRepository
import com.hyangmoa.core_domain.repository.ReportRepository
import com.hyangmoa.core_model.request.TargetRequestDto
import com.hyangmoa.core_model.response.PerfumeCommentResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificCommentViewmodel @Inject constructor(
    private val perfumeCommentRepository: PerfumeCommentRepository,
    private val reportRepository: ReportRepository,
) : ViewModel() {
    private val perfumeCommentState = MutableStateFlow<PerfumeCommentResponseDto?>(null)
    private val likePerfumeCommentState = MutableStateFlow<Boolean>(false)
    private val targetId = MutableStateFlow<Int?>(null)
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
            flow { emit(perfumeCommentRepository.getPerfumeComment(commentId)) }.asResult().collectLatest {
                when (it) {
                    is com.hyangmoa.core_common.Result.Loading -> {
                        SpecificCommentUiState.Loading
                    }

                    is com.hyangmoa.core_common.Result.Success -> {
                        perfumeCommentState.value = it.data
                        likePerfumeCommentState.value = it.data.liked

                    }

                    is Result.Error -> {
                        SpecificCommentUiState.Error
                    }
                }
            }
        }
    }

    fun onReportConfirmClick() {
        if (targetId != null) {
            viewModelScope.launch { reportRepository.reportPerfumeComment(TargetRequestDto(targetId.value.toString())) }
        }
    }

    fun saveReportTargetId(id: Int) {
        targetId.update { id }
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