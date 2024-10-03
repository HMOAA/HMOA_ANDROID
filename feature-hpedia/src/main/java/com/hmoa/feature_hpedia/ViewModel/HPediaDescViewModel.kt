package com.hmoa.feature_hpedia.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.NoteRepository
import com.hmoa.core_domain.repository.PerfumerRepository
import com.hmoa.core_domain.repository.TermRepository
import com.hmoa.core_model.data.HpediaType
import com.hmoa.core_model.response.NoteDescResponseDto
import com.hmoa.core_model.response.PerfumerDescResponseDto
import com.hmoa.core_model.response.TermDescResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HPediaDescViewModel @Inject constructor(
    private val termRepository: TermRepository,
    private val noteRepository: NoteRepository,
    private val perfumerRepository: PerfumerRepository
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
            when (type) {
                HpediaType.TERM.title -> {
                    _type.update { HpediaType.TERM }
                }

                HpediaType.NOTE.title -> {
                    _type.update { HpediaType.NOTE }
                }

                HpediaType.PERFUMER.title -> {
                    _type.update { HpediaType.PERFUMER }
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