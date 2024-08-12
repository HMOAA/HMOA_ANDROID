package com.hyangmoa.feature_perfume.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyangmoa.core_common.Result
import com.hyangmoa.core_common.asResult
import com.hyangmoa.core_domain.repository.PerfumeCommentRepository
import com.hyangmoa.core_model.request.PerfumeCommentRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewPerfumeViewmodel @Inject constructor(
    private val perfumeCommentRepository: PerfumeCommentRepository
) : ViewModel() {
    private val perfumeCommentState = MutableStateFlow<String>("")
    private val _isNewPerfumeCommentSubmitedState = MutableStateFlow<Boolean>(false)
    val isNewPerfumeCommentSubmitedState: StateFlow<Boolean> = _isNewPerfumeCommentSubmitedState

    val uiState: StateFlow<NewPerfumeCommentUiState> =
        combine(perfumeCommentState, _isNewPerfumeCommentSubmitedState) { perfumeComment, isNewPerfumeCommentSubmited ->
            NewPerfumeCommentUiState.CommentData(
                comment = perfumeComment,
                isNewPerfumeCommentSubmited = isNewPerfumeCommentSubmited
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1_000),
            initialValue = NewPerfumeCommentUiState.Loading
        )

    fun onChangePerfumceComment(text: String) {
        perfumeCommentState.update { text }
    }

    fun onSubmitPerfumeComment(perfumeId: Int, text: String) {
        viewModelScope.launch {
            flow {
                emit(
                    perfumeCommentRepository.postPerfumeComment(
                        perfumeId = perfumeId,
                        dto = PerfumeCommentRequestDto(content = text)
                    )
                )
            }.asResult().collectLatest {
                when (it) {
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        _isNewPerfumeCommentSubmitedState.update { true }
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    sealed interface NewPerfumeCommentUiState {
        data object Loading : NewPerfumeCommentUiState
        data class CommentData(
            val comment: String,
            val isNewPerfumeCommentSubmited: Boolean
        ) : NewPerfumeCommentUiState

        data object Error : NewPerfumeCommentUiState
    }
}