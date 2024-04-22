package com.hmoa.feature_perfume.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_model.data.SortType
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.feature_perfume.PerfumeCommentLatestPagingSource
import com.hmoa.feature_perfume.PerfumeCommentLikePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfumeCommentViewmodel @Inject constructor(
    private val perfumeCommentRepository: PerfumeCommentRepository,
    private val reportRepository: ReportRepository,
    private val handle: SavedStateHandle
) : ViewModel() {
    private val TARGET_ID = "targetId"
    val PAGE_SIZE = 10
    private var _sortedLikeCommentsState = MutableStateFlow<PagingData<PerfumeCommentResponseDto>?>(null)
    val sortedLikeCommentsState: StateFlow<PagingData<PerfumeCommentResponseDto>?> = _sortedLikeCommentsState
    private var _sortedLatestCommentsState = MutableStateFlow<PagingData<PerfumeCommentResponseDto>?>(null)
    val sortedLatestCommentsState: StateFlow<PagingData<PerfumeCommentResponseDto>?> = _sortedLatestCommentsState
    private var isSortState = MutableStateFlow(SortType.LATEST)
    private var PERFUME_ID = MutableStateFlow<Int?>(null)
    val uiState: StateFlow<PerfumeCommentUiState> =
        combine(
            _sortedLikeCommentsState,
            sortedLatestCommentsState,
            isSortState
        ) { likeComments, latestComments, isLikeSort ->
            PerfumeCommentUiState.CommentData(
                sortType = isLikeSort,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PerfumeCommentUiState.Loading
        )

    fun latestPerfumeCommentPagingSource(perfumeId: Int) = PerfumeCommentLatestPagingSource(
        perfumeId = perfumeId,
        perfumeCommentRepository = perfumeCommentRepository
    )

    fun likePerfumeCommentPagingSource(perfumeId: Int) = PerfumeCommentLikePagingSource(
        perfumeId = perfumeId,
        perfumeCommentRepository = perfumeCommentRepository
    )

    fun getPagingLatestPerfumeComments(perfumeId: Int?): Flow<PagingData<PerfumeCommentResponseDto>>? {
        if (perfumeId != null) {
            return Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { latestPerfumeCommentPagingSource(perfumeId) }
            ).flow.cachedIn(viewModelScope)
        } else {
            return null
        }
    }

    fun getPagingLikePerfumeComments(perfumeId: Int?): Flow<PagingData<PerfumeCommentResponseDto>>? {
        if (perfumeId != null) {
            return Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { likePerfumeCommentPagingSource(perfumeId) }
            ).flow.cachedIn(viewModelScope)
        } else {
            return null
        }
    }

    fun onClickSortLike() {
        isSortState.update { SortType.LIKE }
    }

    fun onClickSortLatest() {
        isSortState.update { SortType.LATEST }
    }

    fun onClickReport() {
        val id = handle.get<String>(TARGET_ID)
        if (id != null) {
            viewModelScope.launch { reportRepository.reportPerfumeComment(TargetRequestDto(id)) }
        }
    }

    fun saveTargetId(id: String) {
        handle[TARGET_ID] = id
    }

    fun savePerfumeId(id: Int) {
        PERFUME_ID.update { id }
    }

    sealed interface PerfumeCommentUiState {
        data object Loading : PerfumeCommentUiState
        data class CommentData(
            val sortType: SortType,
        ) : PerfumeCommentUiState

        data object Error : PerfumeCommentUiState
    }
}