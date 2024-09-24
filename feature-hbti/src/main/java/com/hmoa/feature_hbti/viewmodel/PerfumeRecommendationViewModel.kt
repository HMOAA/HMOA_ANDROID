package com.hmoa.feature_hbti.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.emitOrThrow
import com.hmoa.core_domain.usecase.GetPerfumeSurveyUseCase
import com.hmoa.core_model.data.PerfumeSurveyContents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfumeRecommendationViewModel @Inject constructor(
    private val getPerfumeSurveyUseCase: GetPerfumeSurveyUseCase
) : ViewModel() {
    private var _perfumeSurveyContentsState = MutableStateFlow<PerfumeSurveyContents?>(null)
    private var _isNextButtonAvailableState = MutableStateFlow<List<Boolean>>(listOf())
    private var _selectedPriceOptionIdsState = MutableStateFlow<List<Int>?>(null)
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
    val uiState: StateFlow<PerfumeRecommendationUiState> = combine(
        _perfumeSurveyContentsState, _isNextButtonAvailableState, _selectedPriceOptionIdsState
    ) { perfumeSurveyContents, isNextButtonAvailable, selectedPriceOptionIds ->
        PerfumeRecommendationUiState.PerfumeRecommendationData(
            contents = perfumeSurveyContents,
            isNextButtonAvailable = isNextButtonAvailable,
            selectedPriceOptionIds = selectedPriceOptionIds

        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PerfumeRecommendationUiState.Loading
    )

    suspend fun getSurveyResult() {
        flow {
            val result = getPerfumeSurveyUseCase.invoke()
            result.emitOrThrow { emit(it) }
        }.asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    _perfumeSurveyContentsState.update { result.data.data }
                }

                is Result.Error -> {

                }

                is Result.Loading -> {

                }
            }
        }
    }

    //설문 조사 저장
    fun postSurveyResult() {
        viewModelScope.launch {
            /** 여기는 서버에 저장하는 부분
             * API 변경되면 그 때 마저 변경 **/
            Log.d("Survey TAG", "서버에 정보 저장 in Perfume Recommendation View Model")
        }
    }

    fun onClickNextButton() {

    }

}

sealed interface PerfumeRecommendationUiState {
    data object Loading : PerfumeRecommendationUiState
    data class PerfumeRecommendationData(
        val contents: PerfumeSurveyContents?,
        val isNextButtonAvailable: List<Boolean>?,
        val selectedPriceOptionIds: List<Int>?
    ) : PerfumeRecommendationUiState

    data object Error : PerfumeRecommendationUiState
}