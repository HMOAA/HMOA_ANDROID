package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotePickResultViewModel @Inject constructor(
    private val hshopRepository: HshopRepository
): ViewModel(){
    private val noteIds = MutableStateFlow<List<Int>>(emptyList())

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

    val uiState: StateFlow<NotePickResultState> = noteIds.mapLatest{
        if(it.isEmpty()) throw Exception("데이터 로딩 오류")
        val requestDto = ProductListRequestDto(productIds = noteIds.value)
        val request = hshopRepository.postNotesSelected(requestDto)
        if (request.errorMessage != null) throw Exception(request.errorMessage!!.message)
        request.data
    }.asResult().map{result ->
        when(result){
            Result.Loading -> NotePickResultState.Loading
            is Result.Success -> NotePickResultState.Success(result.data!!)
            is Result.Error -> {
                if (result.exception.toString().contains("statusCode=401")) {
                    wrongTypeTokenErrorState.update{ true }
                } else {
                    generalErrorState.update{Pair(true, result.exception.message)}
                }
                NotePickResultState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NotePickResultState.Loading
    )
    fun setNoteIds(newNoteIds: List<Int>) = this.noteIds.update{newNoteIds}
}

sealed interface NotePickResultState{
    data object Loading: NotePickResultState
    data object Error: NotePickResultState
    data class Success(val result: PostNoteSelectedResponseDto): NotePickResultState
}