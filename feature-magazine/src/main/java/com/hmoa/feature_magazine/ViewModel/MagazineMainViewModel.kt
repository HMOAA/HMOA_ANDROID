package com.hmoa.feature_magazine.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hmoa.core_model.response.RecentPerfumeResponseDto
import com.hmoa.feature_magazine.MagazinePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MagazineMainViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val magazineRepository : MagazineRepository
) : ViewModel() {
    private val PAGE_SIZE = 5

    private val perfumeFlow = flow{
        val result = perfumeRepository.getRecentPerfumes()
        if (result.errorMessage is ErrorMessage) throw Exception(result.errorMessage!!.message)
        emit(result.data!!)
    }.asResult()

    private val communityFlow = flow{
        val result = magazineRepository.getMagazineTastingComment()
        if (result.errorMessage is ErrorMessage) throw Exception(result.errorMessage!!.message)
        emit(result.data!!)
    }.asResult()

    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorUiState: StateFlow<ErrorUiState> = combine(
        expiredTokenErrorState,
        wrongTypeTokenErrorState,
        unLoginedErrorState,
        generalErrorState
    ) { expiredTokenError, wrongTypeTokenError, unknownError, generalError ->
        ErrorUiState.ErrorData(
            expiredTokenError = expiredTokenError,
            wrongTypeTokenError = wrongTypeTokenError,
            unknownError = unknownError,
            generalError = generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )

    val uiState : StateFlow<MagazineMainUiState> = combine(
        perfumeFlow,
        communityFlow
    ){ perfumes, communities ->
        if (perfumes is Result.Loading || communities is Result.Loading){
            MagazineMainUiState.Loading
        } else if (perfumes is Result.Error || communities is Result.Error){
            val errMsg = if (perfumes is Result.Error) perfumes.exception.message
                else (communities as Result.Error).exception.message
            generalErrorState.update{ Pair(true, errMsg) }
            MagazineMainUiState.Error(errMsg ?: "Error Message is NULL")
        }
        else {
            val perfumeList = (perfumes as Result.Success).data
            val communityList = (communities as Result.Success).data
            MagazineMainUiState.MagazineMain(
                perfumes = perfumeList,
                reviews = communityList
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = MagazineMainUiState.Loading
    )

    fun magazinePagingSource(): Flow<androidx.paging.PagingData<MagazineSummaryResponseDto>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getMagazinePaging()
        }
    ).flow.cachedIn(viewModelScope)

    private fun getMagazinePaging() = MagazinePagingSource(
        magazineRepository = magazineRepository,
    )
}

sealed interface MagazineMainUiState{
    data object Loading : MagazineMainUiState
    data class MagazineMain (
        val perfumes : RecentPerfumeResponseDto,
        val reviews : MagazineTastingCommentResponseDto
    ) : MagazineMainUiState
    data class Error(
        val message : String
    ) : MagazineMainUiState
}
