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
import com.hmoa.feature_perfume.PerfumeCommentPagingSource
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
    private val PERFUME_ID = "perfumeId"

    private var _sortedLikeCommentsState = MutableStateFlow<PagingData<PerfumeCommentResponseDto>?>(null)
    val sortedLikeCommentsState: StateFlow<PagingData<PerfumeCommentResponseDto>?> = _sortedLikeCommentsState
    private var _sortedLatestCommentsState = MutableStateFlow<PagingData<PerfumeCommentResponseDto>?>(null)
    val sortedLatestCommentsState: StateFlow<PagingData<PerfumeCommentResponseDto>?> = _sortedLatestCommentsState
    private var sortedLatestCommentsPage = MutableStateFlow<Int>(0)
    private var sortedLikeCommentsPage = MutableStateFlow<Int>(0)
    private var isSortState = MutableStateFlow<SortType>(SortType.LIKE)
    private var sortedLatestCommentsIsNextPageExists = MutableStateFlow<Boolean>(true)
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

    fun latestPerfumeCommentPagingSource(perfumeId: String?) = PerfumeCommentPagingSource(
        page = sortedLatestCommentsPage.value,
        perfumeId = perfumeId,
        isNextPageExist = sortedLatestCommentsIsNextPageExists.value,
        perfumeCommentRepository = perfumeCommentRepository
    )

    val PAGE_SIZE = 10
    val getPagingLatestPerfumeComments = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { latestPerfumeCommentPagingSource(handle.get<String>(PERFUME_ID)) }
    ).flow.cachedIn(viewModelScope)

    fun addLatestPerfumeComments(perfumeId: Int) {
        viewModelScope.launch {
//            getPagingLatestPerfumeComments(perfumeId).asResult().collectLatest {
//                when (it) {
//                    is Result.Success -> {
//                        _sortedLatestCommentsState.value = it.data
//                        sortedLatestCommentsIsNextPageExists.value // TODO("마지막페이지가 존재하는지 아닌지 확인해야 함")
//                    }
//
//                    is Result.Error -> {
//
//                    }
//
//                    is Result.Loading -> {
//
//                    }
//                }
//            }
        }
    }

    fun addLikePerfumeComments(perfumeId: Int) {


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

    fun judgeSortedComments(): PagingData<PerfumeCommentResponseDto>? {
        when (isSortState.value) {
            SortType.LIKE -> return _sortedLikeCommentsState.value
            SortType.LATEST -> return sortedLatestCommentsState.value
        }
    }

    fun saveTargetId(id: String) {
        handle[TARGET_ID] = id
    }

    fun savePerfumetId(id: String) {
        handle[PERFUME_ID] = id
    }

    sealed interface PerfumeCommentUiState {
        data object Loading : PerfumeCommentUiState
        data class CommentData(
            val sortType: SortType,
        ) : PerfumeCommentUiState

        data object Error : PerfumeCommentUiState
    }
}