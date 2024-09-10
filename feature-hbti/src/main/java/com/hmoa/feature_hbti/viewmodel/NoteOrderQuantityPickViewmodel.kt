package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.feature_hbti.NoteOrderQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteOrderQuantityPickViewmodel @Inject constructor(private val surveyRepository: SurveyRepository) : ViewModel() {
    val noteOrderQuantityChoiceContents: List<String> = listOf("2개", "5개", "8개", "자유롭게 선택")
    val NOTE_ORDER_QUANTITY_CHOICE_IDS: List<Int> = listOf(
        NoteOrderQuantity.TWO.id,
        NoteOrderQuantity.FIVE.id,
        NoteOrderQuantity.EIGHT.id,
        NoteOrderQuantity.NOLIMIT.id
    )
    private val noteOrderQuantityChoiceList =
        listOf(NoteOrderQuantity.TWO, NoteOrderQuantity.FIVE, NoteOrderQuantity.EIGHT, NoteOrderQuantity.NOLIMIT)
    private var _topRecommendedNote = MutableStateFlow<String>("")
    private var _isNextButtonDisabled = MutableStateFlow<Boolean>(false)
    val isNextButtonDisabled: StateFlow<Boolean> = _isNextButtonDisabled
    private var _noteQuantityChoiceAnswersId = MutableStateFlow<List<Int>>(listOf())
    val noteQuantityChoiceAnswersId: StateFlow<List<Int>> = _noteQuantityChoiceAnswersId
    private var _noteOrderQuantityChoice = MutableStateFlow<NoteOrderQuantity>(noteOrderQuantityChoiceList[0])
    val noteOrderQuantityChoice: StateFlow<NoteOrderQuantity> = _noteOrderQuantityChoice

    val uiState: StateFlow<NoteOrderQuantityPickUiState> =
        combine(
            _topRecommendedNote,
            _isNextButtonDisabled,
            _noteQuantityChoiceAnswersId
        ) { topRecommendedNote, isNextButtonDisabled, noteQuantityChoiceAnswersId ->
            NoteOrderQuantityPickUiState.NoteOrderQuantityPickData(
                topRecommendedNote = topRecommendedNote,
                isNextButtonDisabled = isNextButtonDisabled,
                noteQuantityChoiceAnswersId = noteQuantityChoiceAnswersId
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteOrderQuantityPickUiState.NoteOrderQuantityPickData(
                "",
                false,
                noteQuantityChoiceAnswersId = listOf()
            )
        )

    init {
        viewModelScope.launch(Dispatchers.IO) { getTopRecommendedNote() }
    }

    suspend fun getTopRecommendedNote() {
        val result = surveyRepository.getAllSurveyResult()
        _topRecommendedNote.update { result[0].noteName }
    }

    fun updateAnswerOption(answerIds: List<Int>, optionIndex: Int): List<Int> {
        val updatedAnswer: MutableList<Int> = mutableListOf()
        if (answerIds.isNotEmpty()) {
            updatedAnswer.removeAt(0)
        }
        updatedAnswer.add(optionIndex)
        return updatedAnswer
    }

    fun modifyAnswerOption(optionIndex: Int, isGoToSelectedState: Boolean) {
        when (isGoToSelectedState) {
            true -> {
                val updatedAnswerIds = updateAnswerOption(_noteQuantityChoiceAnswersId.value, optionIndex)
                _noteQuantityChoiceAnswersId.update { updatedAnswerIds }
                _noteOrderQuantityChoice.update { noteOrderQuantityChoiceList[optionIndex] }
            }

            false -> {
                _noteQuantityChoiceAnswersId.update { emptyList() }
            }
        }
        _isNextButtonDisabled.update { isGoToSelectedState }
    }
}

sealed interface NoteOrderQuantityPickUiState {
    data class NoteOrderQuantityPickData(
        val topRecommendedNote: String,
        val isNextButtonDisabled: Boolean,
        val noteQuantityChoiceAnswersId: List<Int>
    ) : NoteOrderQuantityPickUiState
}