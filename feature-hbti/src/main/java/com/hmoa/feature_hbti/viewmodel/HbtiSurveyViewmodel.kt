package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.SurveyOptionResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HbtiSurveyViewmodel @Inject constructor(private val surveyRepository: SurveyRepository) : ViewModel() {
    private var _questionsState = MutableStateFlow<List<String>?>(null)
    val questionsState: StateFlow<List<String>?> = _questionsState
    private var _optionsContentState = MutableStateFlow<List<List<String>>?>(null)
    val optionsContentState: StateFlow<List<List<String>>?> = _optionsContentState
    private var _optionsState = MutableStateFlow<List<List<SurveyOptionResponseDto>>?>(null)
    val optionsState: StateFlow<List<List<SurveyOptionResponseDto>>?> = _optionsState
    private var _answersState = MutableStateFlow<SurveyRespondRequestDto?>(null)
    val answersState: StateFlow<SurveyRespondRequestDto?> = _answersState
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

    val uiState: StateFlow<HbtiSurveyUiState> =
        combine(
            _questionsState,
            _optionsContentState,
            _optionsState,
            answersState
        ) { questions, optionsContent, options, answers ->
            HbtiSurveyUiState.HbtiData(
                questions = questions,
                optionsContent = optionsContent,
                options = options,
                answers = answers
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000), initialValue = HbtiSurveyUiState.Loading
        )

    fun updateQuestionState(surveyQuestions: SurveyQuestionsResponseDto?): List<String>? {
        return surveyQuestions?.questions?.map { it.content }
    }

    fun updateOptionContentState(surveyQuestions: SurveyQuestionsResponseDto?): List<List<String>>? {
        return surveyQuestions?.questions?.map { it.answers.map { it.option } }
    }

    fun updateOptionsState(surveyQuestions: SurveyQuestionsResponseDto?): List<List<SurveyOptionResponseDto>>? {
        return surveyQuestions?.questions?.map { it.answers }
    }

    suspend fun getSurveyQuestions() {
        flow { emit(surveyRepository.getSurveyQuestions()) }.asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    _questionsState.update { updateQuestionState(result.data.data) }
                    _optionsContentState.update { updateOptionContentState(result.data.data) }
                    _optionsState.update { updateOptionsState(result.data.data) }
                }

                is Result.Error -> {
                    when (result.exception.message) {
                        ErrorMessageType.EXPIRED_TOKEN.message -> {
                            expiredTokenErrorState.update { true }
                        }

                        ErrorMessageType.WRONG_TYPE_TOKEN.message -> {
                            wrongTypeTokenErrorState.update { true }
                        }

                        ErrorMessageType.UNKNOWN_ERROR.message -> {
                            unLoginedErrorState.update { true }
                        }

                        else -> {
                            generalErrorState.update { Pair(true, result.exception.message) }
                        }
                    }
                }

                Result.Loading -> HbtiSurveyUiState.Loading
            }
        }
    }

    suspend fun postSurveyResponds() {
        if (answersState.value != null) {
            surveyRepository.postSurveyResponds(answersState.value!!)
        }
    }
}

sealed interface HbtiSurveyUiState {
    data object Loading : HbtiSurveyUiState
    data class HbtiData(
        val questions: List<String>?,
        val optionsContent: List<List<String>>?,
        val options: List<List<SurveyOptionResponseDto>>?,
        val answers: SurveyRespondRequestDto?
    ) : HbtiSurveyUiState
}
