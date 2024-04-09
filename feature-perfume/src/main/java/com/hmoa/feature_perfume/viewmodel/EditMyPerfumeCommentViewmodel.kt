package com.hmoa.feature_perfume.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditMyPerfumeCommentViewmodel @Inject constructor(
    private val perfumeCommentRepository: PerfumeCommentRepository
) : ViewModel() {
    private val perfumeCommentState = MutableStateFlow<PerfumeCommentResponseDto?>(null)
    private val likePerfumeCommentState = MutableStateFlow<Boolean>(false)
    private val isNewPerfumeCommentSubmitedState = MutableStateFlow<Boolean>(false)

    val uiState: StateFlow<SpecificCommentUiState> =
        combine(perfumeCommentState, likePerfumeCommentState, isNewPerfumeCommentSubmitedState) { perfumeComment, likePerfume,isNewPerfumeCommentSubmited ->
            SpecificCommentUiState.CommentData(comment = perfumeComment, isLikeComment = likePerfume, isNewPerfumeCommentSubmited = isNewPerfumeCommentSubmited)

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SpecificCommentUiState.Loading
        )

    fun initializePerfumeComment(commentId: Int) {
        viewModelScope.launch {
            flow { emit(perfumeCommentRepository.getPerfumeComment(commentId)) }.asResult().collectLatest {
                when (it) {
                    is com.hmoa.core_common.Result.Loading -> {

                    }

                    is com.hmoa.core_common.Result.Success -> {
                        perfumeCommentState.update { it }
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    fun onChangeLikePerfumeComment() {

    }

    fun onChangePerfumceComment(text: String) {
        viewModelScope.launch {
            perfumeCommentState.value?.content = text
        }
    }

    fun onSubmitPerfumeComment(commentId: Int, text: String) {
        viewModelScope.launch {
            flow {
                emit(
                    perfumeCommentRepository.putPerfumeCommentModify(
                        commentId = commentId,
                        dto = PerfumeCommentRequestDto(content = text)
                    )
                )
            }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        perfumeCommentState.update { it }
                        isNewPerfumeCommentSubmitedState.update { true }
                    }

                    is Result.Error -> {

                    }

                    is Result.Loading -> {

                    }
                }
            }
        }
    }

    sealed interface SpecificCommentUiState {
        data object Loading : SpecificCommentUiState
        data class CommentData(
            val comment: PerfumeCommentResponseDto?,
            val isLikeComment: Boolean,
            val isNewPerfumeCommentSubmited:Boolean
        ) : SpecificCommentUiState

        data object Error : SpecificCommentUiState
    }
}