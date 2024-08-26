package com.hmoa.feature_hbti.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.data.NoteSelect
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.ProductListResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotePickViewmodel @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val hshopRepository: HshopRepository
) : ViewModel() {
    private var _topRecommendedNoteState = MutableStateFlow<String>("")
    private var _notesState = MutableStateFlow<ProductListResponseDto?>(null)
    private var _noteSelectDataState = MutableStateFlow<List<NoteSelect>>(listOf())
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
            _notesState,
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
            initializeIsNoteSelectedList(_notesState.value)
        }
    }

    fun initializeIsNoteSelectedList(noteList: ProductListResponseDto?) {
        var initializedList =
            MutableList(noteList?.data?.size ?: 0) {
                NoteSelect(
                    selectedIndex = 0,
                    isSelected = false,
                    isRecommended = false,
                    productId = 0
                )
            }
        noteList?.data?.mapIndexed { index, productResponseDto ->
            if (productResponseDto.isRecommended) {
                initializedList.set(
                    index,
                    NoteSelect(
                        productId = productResponseDto.productId,
                        selectedIndex = null,
                        isSelected = false,
                        isRecommended = true
                    )
                )
            } else {
                initializedList.set(
                    index,
                    NoteSelect(
                        productId = productResponseDto.productId,
                        selectedIndex = null,
                        isSelected = false,
                        isRecommended = false
                    )
                )
            }
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
                        _notesState.update { result.data.data }
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
        selectedIds.update{requestDto}
        viewModelScope.launch(Dispatchers.IO) {
            flow { emit(hshopRepository.postNotesSelected(ProductListRequestDto(productIds = requestDto))) }.asResult()
                .collectLatest { result ->
                    when (result) {
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
        Log.d(
            "NotepickViewModel",
            "noteOrderQuantity: ${noteOrderQuantity}, selectedNotesOrderQuantity:${selectedNotesOrderQuantity}"
        )
        if (selectedNotesOrderQuantity < noteOrderQuantity) {
            changeNoteSelectData(index, value, data)
        } else if (selectedNotesOrderQuantity == noteOrderQuantity) {
            if (value == false) {
                changeNoteSelectData(index, value, data)
            }
        }
    }

    fun changeNoteSelectData(
        index: Int,
        value: Boolean,
        data: NoteSelect,
    ) {
        val selectIndexUpdatedList = modifySelectedIndexBeforeClick()
        var count = selectIndexUpdatedList.count { it.isSelected }
        val clickedNoteUpdatedList = modifyClickedNoteData(
            updatedList = selectIndexUpdatedList,
            index = index,
            value = value,
            data = data,
            count = count
        )

        _noteSelectDataState.update { clickedNoteUpdatedList }
        _noteOrderIndex.update { count }
    }

    fun modifySelectedIndexBeforeClick(): MutableList<NoteSelect> {
        var count = 1
        val updatedList: MutableList<NoteSelect> = _noteSelectDataState.value.map {
            if (it.isSelected and it.isRecommended == false) {
                count += 1
                NoteSelect(
                    selectedIndex = count,
                    isSelected = it.isSelected,
                    isRecommended = it.isRecommended,
                    productId = it.productId
                )
            } else {
                NoteSelect(
                    selectedIndex = null,
                    isSelected = it.isSelected,
                    isRecommended = it.isRecommended,
                    productId = it.productId
                )
            }
        }.toMutableList()
        return updatedList
    }

    fun modifyClickedNoteData(
        updatedList: MutableList<NoteSelect>,
        index: Int,
        value: Boolean,
        data: NoteSelect,
        count: Int
    ): MutableList<NoteSelect> {
        var count = count
        if (value && data.isRecommended == false) {
            count += 1
            updatedList.set(
                index,
                NoteSelect(
                    selectedIndex = count,
                    isSelected = true,
                    isRecommended = data.isRecommended,
                    productId = data.productId
                )
            )
        } else if (value && data.isRecommended == true) {
            updatedList.set(
                index,
                NoteSelect(
                    selectedIndex = null,
                    isSelected = true,
                    isRecommended = data.isRecommended,
                    productId = data.productId
                )
            )
        } else {
            updatedList.set(
                index,
                NoteSelect(
                    selectedIndex = null,
                    isSelected = false,
                    isRecommended = data.isRecommended,
                    productId = data.productId
                )
            )
        }
        return updatedList
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