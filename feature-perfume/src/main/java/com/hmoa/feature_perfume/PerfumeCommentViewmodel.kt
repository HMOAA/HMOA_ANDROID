package com.hmoa.feature_perfume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_model.data.SortType
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PerfumeCommentViewmodel @Inject constructor(
    private val perfumeCommentRepository: PerfumeCommentRepository
) : ViewModel() {
    private var sortedLikeCommentsState = MutableStateFlow<PerfumeCommentGetResponseDto?>(null)
    private var sortedLatestCommentsState = MutableStateFlow<PerfumeCommentGetResponseDto?>(null)
    private var isSortState = MutableStateFlow<SortType>(SortType.LIKE)

    val uiState: StateFlow<PerfumeCommentUiState> =
        combine(
            sortedLikeCommentsState,
            sortedLatestCommentsState,
            isSortState
        ) { likeComments, latestComments, isLikeSort ->
            PerfumeCommentUiState.CommentData(
                sortedComments = judgeSortedComments(),
                sortType = isLikeSort,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PerfumeCommentUiState.Loading
        )

    fun onClickSortLike() {
        isSortState.update { SortType.LIKE }
    }

    fun onClickSortLatest() {
        isSortState.update { SortType.LATEST }
    }

    fun judgeSortedComments(): PerfumeCommentGetResponseDto? {
        when (isSortState.value) {
            SortType.LIKE -> return sortedLikeCommentsState.value
            SortType.LATEST -> return sortedLatestCommentsState.value
        }
    }

    sealed interface PerfumeCommentUiState {
        data object Loading : PerfumeCommentUiState
        data class CommentData(
            val sortedComments: PerfumeCommentGetResponseDto?,
            val sortType: SortType,
        ) : PerfumeCommentUiState

        data object Error : PerfumeCommentUiState
    }
}