package com.hmoa.feature_magazine.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.response.MagazineListResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hmoa.core_model.response.RecentPerfumeResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val magazineFlow = flow{
        val result = magazineRepository.getMagazineList(0)
        if (result.errorMessage is ErrorMessage) throw Exception(result.errorMessage!!.message)
        emit(result.data!!)
    }.asResult()

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
        magazineFlow,
        perfumeFlow,
        communityFlow
    ){ magazines, perfumes, communities ->
        if (magazines is Result.Loading || perfumes is Result.Loading || communities is Result.Loading){
            MagazineMainUiState.Loading
        } else if (magazines is Result.Error || perfumes is Result.Error || communities is Result.Error){
            val errMsg = if (magazines is Result.Error) magazines.exception.message
            else if (perfumes is Result.Error) perfumes.exception.message
            else (communities as Result.Error).exception.message
            generalErrorState.update{ Pair(true, errMsg) }
            MagazineMainUiState.Error(errMsg ?: "Error Message is NULL")
        }
        else {
            val magazineList = (magazines as Result.Success).data
            val perfumeList = (perfumes as Result.Success).data
            val communityList = (communities as Result.Success).data
            MagazineMainUiState.MagazineMain(
                magazines = magazineList,
                perfumes = perfumeList,
                reviews = communityList
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = MagazineMainUiState.Loading
    )
}

sealed interface MagazineMainUiState{
    data object Loading : MagazineMainUiState
    data class MagazineMain (
        val magazines : MagazineListResponseDto,
        val perfumes : RecentPerfumeResponseDto,
        val reviews : MagazineTastingCommentResponseDto
    ) : MagazineMainUiState
    data class Error(
        val message : String
    ) : MagazineMainUiState
}
