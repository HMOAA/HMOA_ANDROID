package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.response.PerfumeRecommendResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PerfumeRecommendationResultViewModel @Inject constructor(
    private val surveyRepository: SurveyRepository,
) : ViewModel() {
    private var _perfumesState = MutableStateFlow<List<PerfumeRecommendResponseDto>>(listOf())
    val perfumesState: StateFlow<List<PerfumeRecommendResponseDto>> = _perfumesState
    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorState: StateFlow<ErrorUiState> = combine(
        expiredTokenErrorState,
        wrongTypeTokenErrorState,
        unLoginedErrorState,
        generalErrorState
    ) { expiredTokenError, wrongTypeToken, unLoginedError, generalError ->
        ErrorUiState.ErrorData(
            expiredTokenError, wrongTypeToken, unLoginedError, generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )

    val uiState: StateFlow<PerfumeResultUiState> = perfumesState.map {
        PerfumeResultUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PerfumeResultUiState.Loading
    )

    init {
        getPerfumeRecommendationResult()
    }

    fun getPerfumeRecommendationResult() {
        _perfumesState.update { surveyRepository.getPerfumeRecommendsResult().data?.recommendPerfumes ?: listOf() }
    }
}

sealed interface PerfumeResultUiState {
    data object Loading : PerfumeResultUiState
    data class Success(
        val perfumes: List<PerfumeRecommendResponseDto>?
    ) : PerfumeResultUiState

    data object Error : PerfumeResultUiState
}