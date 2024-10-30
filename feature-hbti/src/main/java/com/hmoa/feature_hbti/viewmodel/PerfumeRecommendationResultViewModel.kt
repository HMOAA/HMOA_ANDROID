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
    private var _isPriceSortedSelectedState = MutableStateFlow<Boolean>(true)
    private var _isNoteSortedSelectedState = MutableStateFlow<Boolean>(false)
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
            expiredTokenError = expiredTokenError,
            wrongTypeTokenError = wrongTypeToken,
            unknownError = unLoginedError,
            generalError = generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )

    val uiState: StateFlow<PerfumeResultUiState> =
        combine(
            _perfumesState,
            _isPriceSortedSelectedState,
            _isNoteSortedSelectedState
        ) { perfumes, isPriceSortedSelected, isNoteSortedSelected ->
            PerfumeResultUiState.Success(
                perfumes, isPriceSortedSelected, isNoteSortedSelected
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PerfumeResultUiState.Loading
        )

    init {
        insertPriceSortedPerfumes()
    }

    fun insertPriceSortedPerfumes() {
        _isPriceSortedSelectedState.update { true }
        _isNoteSortedSelectedState.update { false }
        _perfumesState.update {
            surveyRepository.getPriceSortedPerfumeRecommendsResult().data?.recommendPerfumes ?: listOf()
        }
    }

    fun insertNoteSortedPerfumes() {
        _isPriceSortedSelectedState.update { false }
        _isNoteSortedSelectedState.update { true }
        _perfumesState.update {
            surveyRepository.getNoteSortedPerfumeRecommendsResult().data?.recommendPerfumes ?: listOf()
        }
    }
}

sealed interface PerfumeResultUiState {
    data object Loading : PerfumeResultUiState
    data class Success(
        val perfumes: List<PerfumeRecommendResponseDto>?,
        val isPriceSortedSelected: Boolean,
        val isNoteSortedSelected: Boolean
    ) : PerfumeResultUiState

    data object Error : PerfumeResultUiState
}