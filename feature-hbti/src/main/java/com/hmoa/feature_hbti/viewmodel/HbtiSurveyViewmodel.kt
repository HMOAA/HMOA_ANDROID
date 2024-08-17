package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.data.HbtiQuestionItem
import com.hmoa.core_model.data.HbtiQuestionItems
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias QuestionPageIndex = Int
typealias QuestionOptionId = Int

@HiltViewModel
class HbtiSurveyViewmodel @Inject constructor(private val surveyRepository: SurveyRepository) : ViewModel() {
    private var _hbtiQuestionItemsState = MutableStateFlow<HbtiQuestionItems?>(null)
    val hbtiQuestionItemsState: StateFlow<HbtiQuestionItems?> = _hbtiQuestionItemsState
    private var _hbtiAnsewrIdsState = MutableStateFlow<List<List<Int>>?>(null)
    private var _finalQuestionAnswersState = MutableStateFlow<MutableList<QuestionOptionId>?>(null)
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
            _hbtiQuestionItemsState,
            _hbtiAnsewrIdsState,
            _finalQuestionAnswersState
        ) { hbtiQuestionItemsState, hbtiAnsewrIdsState, finalQuestionAnswersState ->
            HbtiSurveyUiState.HbtiData(
                hbtiQuestionItems = hbtiQuestionItemsState,
                hbtiAnswerIds = hbtiAnsewrIdsState,
                finalQuestionAnswers = finalQuestionAnswersState
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily, initialValue = HbtiSurveyUiState.Loading
        )

    fun initializeHbtiQuestionItemsState(surveyQuestions: SurveyQuestionsResponseDto?): MutableMap<Int, HbtiQuestionItem> {
        val initializedQuestionItems = mutableMapOf<Int, HbtiQuestionItem>()
        if (surveyQuestions!!.questions.isNotEmpty()) {
            val hbtiQuestionItems = surveyQuestions.questions.map {
                HbtiQuestionItem(
                    questionId = it.questionId,
                    questionContent = it.content,
                    optionIds = it.answers.map { it.optionId },
                    optionContents = it.answers.map { it.option },
                    isMultipleChoice = it.isMultipleChoice,
                    selectedOptionIds = mutableListOf()
                )
            }
            hbtiQuestionItems.mapIndexed { index, hbtiQuestionItem ->
                initializedQuestionItems[index] = hbtiQuestionItem
            }
        }
        return initializedQuestionItems
    }

    fun initializeHbtiAnswerIdsState(surveyQuestions: SurveyQuestionsResponseDto?): List<List<Int>> {
        val initializedHbtiAnswerIds: List<List<Int>> = List(surveyQuestions.questions.size) { emptyList() }
        return initializedHbtiAnswerIds
    }

    suspend fun getSurveyQuestions() {
        flow { emit(surveyRepository.getSurveyQuestions()) }.asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    _hbtiQuestionItemsState.update {
                        HbtiQuestionItems(
                            hbtiQuestions = initializeHbtiQuestionItemsState(
                                result.data.data
                            )
                        )
                    }
                    _hbtiAnsewrIdsState.update { initializeHbtiAnswerIdsState(result.data.data) }
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

    fun finishSurvey() {
        hbtiQuestionItemsState.value?.hbtiQuestions?.values?.map {
            _finalQuestionAnswersState.value?.addAll(it.selectedOptionIds)
        }
        postSurveyResponds()
    }

    fun postSurveyResponds() {
        if (_finalQuestionAnswersState.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                flow { emit(surveyRepository.postSurveyResponds(SurveyRespondRequestDto(optionIds = _finalQuestionAnswersState.value!!))) }.asResult()
                    .collectLatest { result ->
                        when (result) {
                            is Result.Success -> {
                                viewModelScope.launch {
                                    launch { surveyRepository.deleteAllNotes() }.join()
                                    launch { saveSurveyResultToLocalDB(result.data.data?.recommendNotes) }.join()
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

    fun increaseHbtiQuestionItem_SelectedOption(
        newOptionId: Int,
        isMutipleChoice: Boolean,
        selectedOptionIds: MutableList<Int>
    ): MutableList<Int> {
        when (isMutipleChoice) {
            true -> {
                selectedOptionIds.add(newOptionId)
            }

            false -> {
                if (selectedOptionIds.isEmpty()) {
                    selectedOptionIds.add(newOptionId)
                } else {
                    selectedOptionIds[0] = newOptionId
                }
            }
        }
        return selectedOptionIds
    }

    fun decreaseHbtiQuestionItem_SelectedOption(
        targetOptionId: Int,
        selectedOptionIds: MutableList<Int>
    ): MutableList<Int> {
        selectedOptionIds.remove(targetOptionId)
        return selectedOptionIds
    }

    fun getUpdatedHbtiQuestionItems(page: Int, newHbtiQuestionItem: HbtiQuestionItem): HbtiQuestionItems {
        val newHbtiQuestionItems = mutableMapOf<QuestionPageIndex, HbtiQuestionItem>()
        _hbtiQuestionItemsState.value?.hbtiQuestions?.set(page, newHbtiQuestionItem)
        _hbtiQuestionItemsState.value?.hbtiQuestions?.map {
            newHbtiQuestionItems[it.key] = it.value
        }
        return HbtiQuestionItems(hbtiQuestions = newHbtiQuestionItems)
    }

    fun modifyAnswersToOptionId(
        page: Int,
        optionId: Int,
        currentHbtiQuestionItem: HbtiQuestionItem,
        isGoToSelectedState: Boolean
    ) {
        var updatedSelectedOptionIds = mutableListOf<Int>()
        when (isGoToSelectedState) {
            true -> {
                updatedSelectedOptionIds = increaseHbtiQuestionItem_SelectedOption(
                    newOptionId = optionId,
                    isMutipleChoice = currentHbtiQuestionItem.isMultipleChoice,
                    selectedOptionIds = currentHbtiQuestionItem.selectedOptionIds
                )
            }

            false -> {
                updatedSelectedOptionIds = decreaseHbtiQuestionItem_SelectedOption(
                    targetOptionId = optionId,
                    selectedOptionIds = currentHbtiQuestionItem.selectedOptionIds
                )
            }
        }

        val newHbtiQuestionItem = HbtiQuestionItem(
            questionId = currentHbtiQuestionItem.questionId,
            questionContent = currentHbtiQuestionItem.questionContent,
            optionIds = currentHbtiQuestionItem.optionIds,
            optionContents = currentHbtiQuestionItem.optionContents,
            isMultipleChoice = currentHbtiQuestionItem.isMultipleChoice,
            selectedOptionIds = updatedSelectedOptionIds
        )

        _hbtiQuestionItemsState.update {
            getUpdatedHbtiQuestionItems(
                page = page,
                newHbtiQuestionItem = newHbtiQuestionItem
            )
        }

    }

    fun updateHbtiAnswerIds(hbtiQuestionItems: HbtiQuestionItems?) {
        hbtiQuestionItems.hbtiQuestions.map {

        }
    }
}


sealed interface HbtiSurveyUiState {
    data object Loading : HbtiSurveyUiState
    data class HbtiData(
        val hbtiQuestionItems: HbtiQuestionItems?,
        val hbtiAnswerIds: List<List<Int>>?,
        val finalQuestionAnswers: MutableList<QuestionOptionId>?
    ) : HbtiSurveyUiState
}
