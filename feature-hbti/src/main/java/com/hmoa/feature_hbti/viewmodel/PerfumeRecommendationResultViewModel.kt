package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.response.PerfumeLikeResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PerfumeRecommendationResultViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository
) : ViewModel() {

    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorState: StateFlow<ErrorUiState> = combine(
        expiredTokenErrorState,
        wrongTypeTokenErrorState,
        unLoginedErrorState,
        generalErrorState
    ){ expiredTokenError, wrongTypeToken, unLoginedError, generalError ->
        ErrorUiState.ErrorData(
            expiredTokenError, wrongTypeToken, unLoginedError, generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )

    val uiState : StateFlow<PerfumeResultUiState> = flow{
        /** 서버로부터 추천 결과에 대한 값을 받아옴 */
        val result = surveyRepository.getSurveyQuestions()
        if (result.errorMessage is ErrorMessage){throw Exception(result.errorMessage!!.message)}
        emit(result)
    }.asResult().map{result ->
        when(result){
            Result.Loading -> {
                PerfumeResultUiState.Loading
            }
            is Result.Success -> {
                /** 데이터가 들어가야 하는 공간 */
                PerfumeResultUiState.Success(null)
            }
            is Result.Error -> {
                generalErrorState.update{ Pair(true, result.exception.message) }
                PerfumeResultUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PerfumeResultUiState.Loading
    )
}

sealed interface PerfumeResultUiState{
    data object Loading: PerfumeResultUiState
    data class Success(
        val perfumes: List<PerfumeLikeResponseDto>?
    ): PerfumeResultUiState
    data object Error: PerfumeResultUiState
}