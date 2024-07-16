package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.SurveyOptionResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HbtiSurveyViewmodel @Inject constructor(private val surveyRepository: SurveyRepository) : ViewModel() {
    private var _questionsState = MutableStateFlow<List<String>?>(null)
    private var _optionsContentState = MutableStateFlow<List<List<String>>?>(null)
    private var _optionsState = MutableStateFlow<List<List<SurveyOptionResponseDto>>?>(null)
    private var _answersState =
        MutableStateFlow<SurveyRespondRequestDto?>(SurveyRespondRequestDto(optionIds = arrayListOf(10)))
    private var _isCompletedSurvey = MutableStateFlow<Boolean>(false)
    val isCompletedSurvey = _isCompletedSurvey
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
            _answersState
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

    fun initializeAnswerState(surveyQuestions: SurveyQuestionsResponseDto?): MutableList<Int> {
        val size = surveyQuestions?.questions?.size ?: 0
        val answers: MutableList<Int> = MutableList(size) { 0 }
        return answers
    }

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
                    _answersState.update { SurveyRespondRequestDto(optionIds = initializeAnswerState(result.data.data)) }
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

    suspend fun saveSurveyResultToLocalDB(result: List<NoteResponseDto>?) {
        result?.forEach {
            surveyRepository.insertSurveryResult(it)
        }
    }

    fun postSurveyResponds() {
        if (_answersState.value != null) {
            viewModelScope.launch {
                flow { emit(surveyRepository.postSurveyResponds(_answersState.value!!)) }.asResult()
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> {
                                viewModelScope.launch {
                                    val jobSavesurveyResult =
                                        launch { saveSurveyResultToLocalDB(result.data.data?.recommendNotes) }
                                    jobSavesurveyResult.join()
                                    _isCompletedSurvey.update { true }
                                }
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

                            is Result.Loading -> {
                                HbtiSurveyUiState.Loading
                            }
                        }
                    }
            }
        }
    }

    fun modifyAnswers(page: Int, optionId: Int, answers: MutableList<Int>?): MutableList<Int>? {
        answers?.set(page, optionId)
        if (answers != null) {
            _answersState.update { SurveyRespondRequestDto(optionIds = answers) }
        }
        return answers
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
