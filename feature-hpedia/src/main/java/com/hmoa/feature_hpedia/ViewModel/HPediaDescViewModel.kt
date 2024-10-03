package com.hmoa.feature_hpedia.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.usecase.GetNoteUseCase
import com.hmoa.core_domain.usecase.GetPerfumerUseCase
import com.hmoa.core_domain.usecase.GetTermUseCase
import com.hmoa.core_model.data.HpediaType
import com.hmoa.core_model.response.NoteDescResponseDto
import com.hmoa.core_model.response.PerfumerDescResponseDto
import com.hmoa.core_model.response.TermDescResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HPediaDescViewModel @Inject constructor(
    private val getTermUseCase: GetTermUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val perfumerUseCase: GetPerfumerUseCase
) : ViewModel() {

    private var _id = MutableStateFlow<Int?>(null)
    private var _type = MutableStateFlow<HpediaType>(HpediaType.TERM)
    val type get() = _type.asStateFlow()

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

    val uiState: StateFlow<HPediaDescUiState> = _id.filterNotNull().map{ id ->
        val result = when(type.value){
            HpediaType.TERM -> termRepository.getTerm(id)
            HpediaType.NOTE -> noteRepository.getNote(id)
            HpediaType.PERFUMER -> perfumerRepository.getPerfumer(id)
        }
        result
    }.asResult().map{result ->
        when(result){
            Result.Loading -> HPediaDescUiState.Loading
            is Result.Error -> {
                handleErrorType(
                    error = result.exception,
                    onExpiredTokenError = { expiredTokenErrorState.update { true } },
                    onWrongTypeTokenError = { wrongTypeTokenErrorState.update { true } },
                    onUnknownError = { unLoginedErrorState.update { true } },
                    onGeneralError = {generalErrorState.update {Pair(true,result.exception.message)}}
                )
                HPediaDescUiState.Error
            }
            is Result.Success -> {
                when(type.value){
                    HpediaType.TERM -> {
                        val data = result.data.data!!.data as TermDescResponseDto
                        HPediaDescUiState.HPediaDesc(
                            title = data.termTitle,
                            subTitle = data.termEnglishTitle,
                            content = data.content
                        )
                    }
                    HpediaType.NOTE -> {
                        val data = result.data.data!!.data as NoteDescResponseDto
                        HPediaDescUiState.HPediaDesc(
                            title = data.noteTitle,
                            subTitle = data.noteSubtitle,
                            content = data.content
                        )
                    }
                    HpediaType.PERFUMER -> {
                        val data = result.data.data!!.data as PerfumerDescResponseDto
                        HPediaDescUiState.HPediaDesc(
                            title = data.perfumerTitle,
                            subTitle = data.perfumerSubTitle,
                            content = data.content
                        )
                    }
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HPediaDescUiState.Loading
    )

    fun setInfo(type: String?, id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            _id.update { id }

            if (id != null) {
                when (type) {
                    HpediaType.TERM.title -> {
                        _type.update { HpediaType.TERM }
                        getTermDesc(id)
                    }

                    HpediaType.NOTE.title -> {
                        _type.update { HpediaType.NOTE }
                        getNote(id)
                    }

                    HpediaType.PERFUMER.title -> {
                        _type.update { HpediaType.PERFUMER }
                        getPerfumer(id)
                    }
                }
            }
        }
    }

    suspend fun getTermDesc(id: Int) {
        getTermUseCase(id).asResult().collectLatest { result ->
            when (result) {
                is Result.Error -> HPediaDescUiState.Error
                Result.Loading -> HPediaDescUiState.Loading
                is Result.Success -> {
                    termDesc.update { result.data }
                }
            }
        }
    }

    suspend fun getNote(id: Int) {
        getNoteUseCase(id).asResult().collectLatest { result ->
            when (result) {
                is Result.Error -> HPediaDescUiState.Error
                Result.Loading -> HPediaDescUiState.Loading
                is Result.Success -> {
                    noteDesc.update { result.data }
                }
            }
        }
    }

    suspend fun getPerfumer(id: Int) {
        perfumerUseCase(id).asResult().collectLatest { result ->
            when (result) {
                is Result.Error -> HPediaDescUiState.Error
                Result.Loading -> HPediaDescUiState.Loading
                is Result.Success -> {
                    perfumerDesc.update { result.data }
                }
            }
        }
    }
}

sealed interface HPediaDescUiState {
    data object Error : HPediaDescUiState
    data class HPediaDesc(
        val title: String,
        val subTitle: String,
        val content: String
    ) : HPediaDescUiState

    data object Loading : HPediaDescUiState
}