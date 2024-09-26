package com.hmoa.feature_perfume.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.PerfumeCommentRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_domain.usecase.UpdateLikePerfumeCommentUseCase
import com.hmoa.core_domain.entity.data.SortType
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.feature_perfume.PerfumeCommentLatestPagingSource
import com.hmoa.feature_perfume.PerfumeCommentLikePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfumeCommentViewmodel @Inject constructor(
    private val updateLikePerfumeComment: UpdateLikePerfumeCommentUseCase,
    private val perfumeCommentRepository: PerfumeCommentRepository,
    private val reportRepository: ReportRepository,
    private val loginRepository: LoginRepository,
    private val handle: SavedStateHandle
) : ViewModel() {
    private var authToken = MutableStateFlow<String?>(null)
    private var hasToken = false
    private var _unLoginedErrorState = MutableStateFlow<Boolean>(false)
    val unLoginedErrorState: StateFlow<Boolean> = _unLoginedErrorState
    private val TARGET_ID = "targetId"
    val PAGE_SIZE = 10
    private var _sortedLikeCommentsState = MutableStateFlow<PagingData<PerfumeCommentResponseDto>?>(null)
    val sortedLikeCommentsState: StateFlow<PagingData<PerfumeCommentResponseDto>?> = _sortedLikeCommentsState
    private var _sortedLatestCommentsState = MutableStateFlow<PagingData<PerfumeCommentResponseDto>?>(null)
    val sortedLatestCommentsState: StateFlow<PagingData<PerfumeCommentResponseDto>?> = _sortedLatestCommentsState
    private var sortTypeState = MutableStateFlow(SortType.LATEST)
    private var PERFUME_ID = MutableStateFlow<Int?>(null)
    val uiState: StateFlow<PerfumeCommentUiState> =
        combine(
            sortTypeState,
            _sortedLikeCommentsState
        ) { sortType, likecomments ->
            PerfumeCommentUiState.CommentData(
                sortType = sortType,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PerfumeCommentUiState.Loading
        )

    init {
        getAuthToken()
    }

    fun getHasToken(): Boolean {
        return hasToken
    }

    private fun getAuthToken() {
        viewModelScope.launch {
            loginRepository.getAuthToken().onEmpty { }.collectLatest {
                authToken.value = it
                hasToken = true
            }
        }
    }

    fun notifyLoginNeed() {
        _unLoginedErrorState.update { true }
    }

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

        sortTypeState.update { SortType.LIKE }
    }

    fun onClickSortLatest() {
        sortTypeState.update { SortType.LATEST }
    }

    fun onClickReport() {
        val id = handle.get<String>(TARGET_ID)
        if (id != null) {
            viewModelScope.launch { reportRepository.reportPerfumeComment(TargetRequestDto(id)) }
        }
    }

    fun updatePerfumeCommentLike(like: Boolean, commentId: Int) {

        if (hasToken) {
            viewModelScope.launch(Dispatchers.IO) {
                updateLikePerfumeComment(like, commentId)
            }
        } else {
            _unLoginedErrorState.update { true }
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