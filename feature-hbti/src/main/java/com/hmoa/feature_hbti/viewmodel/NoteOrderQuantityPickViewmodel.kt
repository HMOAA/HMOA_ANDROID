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

    private val noteOrderQuantityChoiceList =
        listOf(NoteOrderQuantity.TWO, NoteOrderQuantity.FIVE, NoteOrderQuantity.EIGHT, NoteOrderQuantity.NOLIMIT)
    private var _topRecommendedNote = MutableStateFlow<String>("")
    private var _noteOrderQuantityChoiceNameList =
        MutableStateFlow<List<String>>(listOf("2개", "5개", "8개 31,900원", "자유롭게 선택"))
    private var _noteOrderQuantityChoice = MutableStateFlow<NoteOrderQuantity>(noteOrderQuantityChoiceList[0])
    val noteOrderQuantityChoice: StateFlow<NoteOrderQuantity> = _noteOrderQuantityChoice
    private var _isPickCompleted = MutableStateFlow<Boolean>(false)
    val isPickCompleted: StateFlow<Boolean> = _isPickCompleted

    val uiState: StateFlow<NoteOrderQuantityPickUiState> =
        combine(
            _topRecommendedNote,
            _noteOrderQuantityChoiceNameList,
            _noteOrderQuantityChoice
        ) { topRecommendedNote, choiceList, noteOrderQuantityChoice ->
            NoteOrderQuantityPickUiState.NoteOrderQuantityPickData(
                topRecommendedNote = topRecommendedNote,
                choiceList = choiceList,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteOrderQuantityPickUiState.NoteOrderQuantityPickData(
                "",
                listOf("", "", "", "")
            )
        )

    init {
        viewModelScope.launch(Dispatchers.IO) { getTopRecommendedNote() }
    }

    suspend fun getTopRecommendedNote() {
        val result = surveyRepository.getAllSurveyResult()
        _topRecommendedNote.update { result[0].noteName }
    }

    fun saveNoteOrderQuantityChoice(choiceIndex: Int) {
        _noteOrderQuantityChoice.update { noteOrderQuantityChoiceList[choiceIndex] }
    }

    fun changePickState(isCompleted: Boolean) {
        _isPickCompleted.update { isCompleted }
    }
}

sealed interface NoteOrderQuantityPickUiState {
    data class NoteOrderQuantityPickData(
        val topRecommendedNote: String,
        val choiceList: List<String>
    ) : NoteOrderQuantityPickUiState
}