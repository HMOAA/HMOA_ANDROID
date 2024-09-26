package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.*
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_domain.entity.data.NoteSelect
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.ProductListResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotePickViewmodel @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val hshopRepository: HshopRepository
) : ViewModel() {
    private var _topRecommendedNoteState = MutableStateFlow<String>("")
    private var _noteProductsState = MutableStateFlow<ProductListResponseDto?>(null)
    private var _noteSelectDataState = MutableStateFlow<List<NoteSelect>>(listOf())
    val topRecommendedNoteState: StateFlow<String> = _topRecommendedNoteState
    val noteProductState: StateFlow<ProductListResponseDto?> = _noteProductsState
    val noteSelectDataState: StateFlow<List<NoteSelect>> = _noteSelectDataState
    val selectedIds = MutableStateFlow<List<Int>>(emptyList())
    private var _noteOrderIndex = MutableStateFlow<Int>(1)
    private var _isCompletedNoteSelected = MutableStateFlow<Boolean>(false)
    val isCompletedNoteSelected: StateFlow<Boolean> = _isCompletedNoteSelected
    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))

    val uiState: StateFlow<NotePickUiState> =
        combine(
            _topRecommendedNoteState,
            _noteProductsState,
            _noteSelectDataState,
            _noteOrderIndex
        ) { topRecommendedNote, notes, noteSelectData, noteOrderIndex ->
            NotePickUiState.NotePickData(
                topRecommendedNote = topRecommendedNote,
                noteProductList = notes,
                noteSelectData = noteSelectData,
                noteOrderIndex = noteOrderIndex
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000), initialValue = NotePickUiState.Loading
        )

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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTopRecommendedNote()
            launch { getNoteProducts() }.join()
            initializeIsNoteSelectedList(_noteProductsState.value)
        }
    }

    fun initializeIsNoteSelectedList(noteList: ProductListResponseDto?) {
        var initializedList =
            MutableList(noteList?.data?.size ?: 0) {
                NoteSelect(
                    nodeFaceIndex = null,
                    isSelected = false,
                    isRecommended = false,
                    productId = 0 //초기값
                )
            }
        noteList?.data?.mapIndexed { index, productResponseDto ->
            initializedList.set(
                index,
                NoteSelect(
                    productId = productResponseDto.productId,
                    nodeFaceIndex = null,
                    isSelected = false,
                    isRecommended = productResponseDto.isRecommended
                )
            )
        }
        _noteSelectDataState.update { initializedList }
    }

    suspend fun getTopRecommendedNote() {
        val result = surveyRepository.getAllSurveyResult()
        _topRecommendedNoteState.update { result[0].noteName }
    }

    suspend fun getNoteProducts() {
        flow { emit(hshopRepository.getNotesProduct()) }.asResult().collectLatest { result ->
            when (result) {
                is com.hmoa.core_common.Result.Success -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        _noteProductsState.update { result.data.data }
                    }
                }

                is com.hmoa.core_common.Result.Error -> {
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

                is com.hmoa.core_common.Result.Loading -> {
                    NotePickUiState.Loading
                }
            }
        }
    }

    fun postNoteSelected() {
        val requestDto = _noteSelectDataState.value.filter { it.isSelected }.map { it.productId }
        selectedIds.update { requestDto }
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                val result = hshopRepository.postNotesSelected(ProductListRequestDto(productIds = requestDto))
                result.emitOrThrow { emit(it) }
            }
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Error -> {
                            handleErrorType(
                                error = result.exception,
                                onExpiredTokenError = { expiredTokenErrorState.update { true } },
                                onWrongTypeTokenError = { wrongTypeTokenErrorState.update { true } },
                                onUnknownError = { unLoginedErrorState.update { true } },
                                onGeneralError = { generalErrorState.update { Pair(true, result.exception.message) } }
                            )
                        }

                        Result.Loading -> {}
                        is Result.Success -> {
                            _isCompletedNoteSelected.update { true }
                        }
                    }
                }
        }
    }

    fun handleNoteSelectData(
        index: Int,
        value: Boolean,
        data: NoteSelect,
        noteOrderQuantity: Int,
        selectedNotesOrderQuantity: Int
    ) {
        if (isAvailableToAddNoteSelect(selectedNotesOrderQuantity, noteOrderQuantity) || isAvailableToCancelNoteSelect(
                selectedNotesOrderQuantity,
                noteOrderQuantity,
                value
            )
        ) {
            var noteSelectData = makeDeepCopyOfNoteSelectData(_noteSelectDataState.value)
            noteSelectData = changeNoteSelectData(index, value, data, noteSelectData)
            noteSelectData = reorderNoteFaceIndex(noteSelectData)
            _noteSelectDataState.update { noteSelectData }
            _noteOrderIndex.update { countSelectedNote(noteSelectData) }
        }
    }

    fun isAvailableToCancelNoteSelect(
        selectedNotesOrderQuantity: Int,
        noteOrderQuantity: Int,
        value: Boolean
    ): Boolean {
        if ((selectedNotesOrderQuantity == noteOrderQuantity) && value == false) {
            return true
        }
        return false
    }

    fun isAvailableToAddNoteSelect(selectedNotesOrderQuantity: Int, noteOrderQuantity: Int): Boolean {
        if (selectedNotesOrderQuantity < noteOrderQuantity) {
            return true
        }
        return false
    }

    fun countSelectedNote(noteSelectData: MutableList<NoteSelect>): Int {
        return noteSelectData.count { it.isSelected == true }
    }

    fun changeNoteSelectData(
        index: Int,
        value: Boolean,
        data: NoteSelect,
        noteSelectData: MutableList<NoteSelect>
    ): MutableList<NoteSelect> {
        noteSelectData.set(
            index,
            NoteSelect(
                productId = data.productId,
                isSelected = value,
                nodeFaceIndex = data.nodeFaceIndex,
                isRecommended = data.isRecommended
            )
        )
        return noteSelectData
    }

    fun reorderNoteFaceIndex(noteSelectData: MutableList<NoteSelect>): MutableList<NoteSelect> {
        var result = mutableListOf<NoteSelect>()
        val recommendAndSelectedNoteNum = noteSelectData.filter { it.isRecommended and it.isSelected }.size
        var nodeFaceIndex = recommendAndSelectedNoteNum
        noteSelectData.map {
            if (it.isSelected == true && it.isRecommended == false) {
                nodeFaceIndex += 1
                result.add(
                    NoteSelect(
                        productId = it.productId,
                        isSelected = it.isSelected,
                        nodeFaceIndex = nodeFaceIndex,
                        isRecommended = it.isRecommended
                    )
                )
            } else {
                result.add(it)
            }
        }
        return result
    }

    fun makeDeepCopyOfNoteSelectData(noteSelectData: List<NoteSelect>): MutableList<NoteSelect> {
        var result = mutableListOf<NoteSelect>()
        noteSelectData.map {
            result.add(
                NoteSelect(
                    productId = it.productId,
                    isSelected = it.isSelected,
                    nodeFaceIndex = it.nodeFaceIndex,
                    isRecommended = it.isRecommended
                )
            )
        }
        return result
    }

}

sealed interface NotePickUiState {
    data object Loading : NotePickUiState
    data class NotePickData(
        val topRecommendedNote: String,
        val noteProductList: ProductListResponseDto?,
        val noteSelectData: List<NoteSelect>,
        val noteOrderIndex: Int,
    ) : NotePickUiState
}