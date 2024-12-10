package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.emitOrThrow
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_domain.usecase.CalculateMinAndMaxPriceOutOfStringUseCase
import com.hmoa.core_domain.usecase.GetPerfumeSurveyUseCase
import com.hmoa.core_model.PerfumeRecommendType
import com.hmoa.core_model.data.NoteCategoryTag
import com.hmoa.core_model.data.PerfumeSurveyContents
import com.hmoa.core_model.request.PerfumeSurveyAnswerRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfumeRecommendationViewModel @Inject constructor(
    private val getPerfumeSurveyUseCase: GetPerfumeSurveyUseCase,
    private val calculateMinAndMaxPriceOutOfStringUseCase: CalculateMinAndMaxPriceOutOfStringUseCase,
    private val surveyRepository: SurveyRepository
) : ViewModel() {
    private var _perfumeSurveyContentsState = MutableStateFlow<PerfumeSurveyContents?>(null)
    val perfumeSurveyContentsState: StateFlow<PerfumeSurveyContents?> = _perfumeSurveyContentsState
    private var _isNextButtonAvailableState = MutableStateFlow<List<Boolean>>(List(2) { false }) // price, note 2가지 질문
    val isNextButtonAvailableState: StateFlow<List<Boolean>> = _isNextButtonAvailableState
    private var _selectedPriceOptionIdsState = MutableStateFlow<List<Int>?>(emptyList())
    val selectedPriceOptionIdsState: StateFlow<List<Int>?> = _selectedPriceOptionIdsState
    private var _selectedNoteTagsOptionState = MutableStateFlow<List<String>>(listOf())
    val selectedNoteTagsOptionState: StateFlow<List<String>> = _selectedNoteTagsOptionState
    private var _noteCategoryTagsState = MutableStateFlow<List<NoteCategoryTag>?>(emptyList())
    val noteCategoryTagsState: StateFlow<List<NoteCategoryTag>?> = _noteCategoryTagsState
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
        _perfumeSurveyContentsState,
        _isNextButtonAvailableState,
        _selectedPriceOptionIdsState,
        _selectedNoteTagsOptionState,
        _noteCategoryTagsState
    ) { perfumeSurveyContents, isNextButtonAvailable, selectedPriceOptionIds, selectedNoteTagsOption, noteCategoryTags ->
        PerfumeRecommendationUiState.PerfumeRecommendationData(
            contents = perfumeSurveyContents,
            isNextButtonAvailable = isNextButtonAvailable,
            selectedPriceOptionIds = selectedPriceOptionIds,
            selectedNoteTagsOption = selectedNoteTagsOption,
            noteCategoryTags = noteCategoryTags
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
                    _noteCategoryTagsState.update { result.data.data?.noteCategoryTags }
                    _isNextButtonAvailableState.update { List(2) { false } } //price, note 2페이지에 대한 버튼 상태 초기화
                }

                is Result.Error -> {

                }

                is Result.Loading -> {
                    PerfumeRecommendationUiState.Loading
                }
            }
        }
    }

    fun postSurveyResult(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val dto = mapOptionIdAndNoteTagsToChangePostSurveyAnswer(
                selectedPriceOptionIds = _selectedPriceOptionIdsState.value,
                perfumeSurveyContents = _perfumeSurveyContentsState.value
            )
            postSurveyPriceSortedResult(dto)
            postSurveyNoteSortedResult(dto)
            onSuccess()
        }
    }

    suspend fun postSurveyPriceSortedResult(dto: PerfumeSurveyAnswerRequestDto) {
        flow {
            val result =
                surveyRepository.postPerfumeSurveyAnswers(dto = dto, recommendType = PerfumeRecommendType.PRICE)
            result.emitOrThrow { emit(it) }
        }.asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        if (result.data.data != null) {
                            surveyRepository.savePriceSortedPerfumeRecommendsResult(result.data.data!!)
                        }
                    }
                }
            }
    }

    suspend fun postSurveyNoteSortedResult(dto: PerfumeSurveyAnswerRequestDto) {
        flow {
            val result = surveyRepository.postPerfumeSurveyAnswers(dto = dto, recommendType = PerfumeRecommendType.NOTE)
            result.emitOrThrow { emit(it) }
        }.asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        if (result.data.data != null) {
                            surveyRepository.saveNoteSortedPerfumeRecommendsResult(result.data.data!!)
                        }
                    }
                }
            }
    }

    fun mapOptionIdAndNoteTagsToChangePostSurveyAnswer(
        selectedPriceOptionIds: List<Int>?,
        perfumeSurveyContents: PerfumeSurveyContents?
    ): PerfumeSurveyAnswerRequestDto {
        val optionId = selectedPriceOptionIds?.get(0)
        val optionIndex = perfumeSurveyContents?.priceQuestionOptionIds?.indexOf(optionId)
        val priceRange = perfumeSurveyContents?.priceQuestionOptions?.get(optionIndex!!) ?: ""
        val minAndMaxPrice = calculateMinAndMaxPriceOutOfStringUseCase.invoke(priceRange)
        val dto = PerfumeSurveyAnswerRequestDto(
            minPrice = minAndMaxPrice.first,
            maxPrice = minAndMaxPrice.second,
            notes = selectedNoteTagsOptionState.value
        )

        return dto
    }

    fun handlePriceQuestionAnswer(optionIndex: Int, isGoToSelectedState: Boolean) {
        val selectedOptionId = _perfumeSurveyContentsState.value?.priceQuestionOptionIds?.get(optionIndex)!!
        var result: List<Int>
        if (isGoToSelectedState) {
            result = addNewPriceOption(
                selectedOptionId,
                _perfumeSurveyContentsState.value?.isPriceMultipleChoice!!,
                _selectedPriceOptionIdsState.value
            )

        } else {
            result = substractPriceOption(
                selectedOptionId,
                _perfumeSurveyContentsState.value?.isPriceMultipleChoice!!,
                _selectedPriceOptionIdsState.value
            )
        }
        _selectedPriceOptionIdsState.update { result }

        handleIsNextAvailableState()
    }

    fun handleIsNextAvailableState() {
        val updatedIsNextAvailableState = mutableListOf<Boolean>()
        if (_selectedPriceOptionIdsState.value.isNullOrEmpty()) {
            updatedIsNextAvailableState.add(false)
        } else {
            updatedIsNextAvailableState.add(true)
        }

        if (_selectedNoteTagsOptionState.value.isEmpty()) {
            updatedIsNextAvailableState.add(false)
        } else {
            updatedIsNextAvailableState.add(true)
        }
        _isNextButtonAvailableState.update { updatedIsNextAvailableState }
    }

    fun addNewPriceOption(optionId: Int, isMultipleChoice: Boolean, selectedPriceOptionIds: List<Int>?): List<Int> {
        val updatedPriceOptionsIds = mutableListOf<Int>()
        if (isMultipleChoice) {
            selectedPriceOptionIds?.map {
                updatedPriceOptionsIds.add(it)
            }
        }
        updatedPriceOptionsIds.add(optionId)
        return updatedPriceOptionsIds
    }

    fun substractPriceOption(optionId: Int, isMultipleChoice: Boolean, selectedPriceOptionIds: List<Int>?): List<Int> {
        val updatedPriceOptionsIds = mutableListOf<Int>()
        if (isMultipleChoice) {
            selectedPriceOptionIds?.map {
                if (optionId != it) {
                    updatedPriceOptionsIds.add(it)
                }
            }
        }
        return updatedPriceOptionsIds
    }

    fun handleNoteQuestionAnswer(note: String, categoryIndex: Int, noteIndex: Int, isGotoState: Boolean) {
        _noteCategoryTagsState.update { modifyNoteCategoryTags(categoryIndex, noteIndex, isGotoState) }
        if (isGotoState) {
            _selectedNoteTagsOptionState.update { addNoteTagOption(note, _selectedNoteTagsOptionState.value) }
        } else {
            _selectedNoteTagsOptionState.update { deleteNoteTagOption(note, _selectedNoteTagsOptionState.value) }
        }

        handleIsNextAvailableState()
    }

    fun modifyNoteCategoryTags(categoryIndex: Int, noteIndex: Int, isGotoState: Boolean): List<NoteCategoryTag> {
        val updatedNoteQuestionAnswer = mutableListOf<NoteCategoryTag>()
        _noteCategoryTagsState.value?.mapIndexed { index, noteCategoryTag ->
            val isSelectedNotes = mutableListOf<Boolean>()
            noteCategoryTag.isSelected.mapIndexed { isSelectedIndex, b ->
                if (categoryIndex == index && noteIndex == isSelectedIndex) {
                    isSelectedNotes.add(isGotoState)
                } else {
                    isSelectedNotes.add(b)
                }
            }

            updatedNoteQuestionAnswer.add(
                NoteCategoryTag(
                    category = noteCategoryTag.category,
                    note = noteCategoryTag.note,
                    isSelected = isSelectedNotes
                )
            )
        }
        return updatedNoteQuestionAnswer
    }

    fun addNoteTagOption(noteTag: String, selectedNoteTagsOption: List<String>?): List<String> {
        val updatedSelectedNoteTagsOption = mutableListOf<String>(noteTag)
        selectedNoteTagsOption?.map {
            updatedSelectedNoteTagsOption.add(it)
        }
        return updatedSelectedNoteTagsOption
    }

    fun deleteNoteTagOption(noteTag: String, selectedNoteTagsOption: List<String>?): List<String> {
        val updatedSelectedNoteTagsOption = mutableListOf<String>()
        selectedNoteTagsOption?.map {
            if (it != noteTag) {
                updatedSelectedNoteTagsOption.add(it)
            }
        }
        return updatedSelectedNoteTagsOption
    }

    fun onDeleteNoteTagOption(noteTag: String) {
        val noteTagIndexes = findCategoryIndexAndNoteIndex(noteTag, _noteCategoryTagsState.value)
        handleNoteQuestionAnswer(
            note = noteTag,
            categoryIndex = noteTagIndexes.first,
            noteIndex = noteTagIndexes.second,
            false
        )
    }

    fun deleteAllNoteTagOptions() {
        _selectedNoteTagsOptionState.update { listOf() }
    }

    fun findCategoryIndexAndNoteIndex(noteTag: String, noteCategoryTags: List<NoteCategoryTag>?): Pair<Int, Int> {
        var categoryIndex = 0
        var noteIndex = 0
        noteCategoryTags?.mapIndexed { _categoryIndex, noteCategoryTag ->
            noteCategoryTag.note.mapIndexed { _noteIndex, tag ->
                if (noteTag == tag) {
                    categoryIndex = _categoryIndex
                    noteIndex = _noteIndex
                }
            }
        }
        return Pair(categoryIndex, noteIndex)
    }

}

sealed interface PerfumeRecommendationUiState {
    data object Loading : PerfumeRecommendationUiState
    data class PerfumeRecommendationData(
        val contents: PerfumeSurveyContents?,
        val isNextButtonAvailable: List<Boolean>,
        val selectedPriceOptionIds: List<Int>?,
        val selectedNoteTagsOption: List<String>?,
        val noteCategoryTags: List<NoteCategoryTag>?,
    ) : PerfumeRecommendationUiState

    data object Error : PerfumeRecommendationUiState
}