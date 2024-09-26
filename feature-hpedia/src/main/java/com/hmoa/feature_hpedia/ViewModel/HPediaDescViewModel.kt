package com.hmoa.feature_hpedia.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.usecase.GetNoteUseCase
import com.hmoa.core_domain.usecase.GetPerfumerUseCase
import com.hmoa.core_domain.usecase.GetTermUseCase
import com.hmoa.core_domain.entity.data.HpediaType
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
    private var termDesc = MutableStateFlow<TermDescResponseDto?>(null)
    private var noteDesc = MutableStateFlow<NoteDescResponseDto?>(null)
    private var perfumerDesc = MutableStateFlow<PerfumerDescResponseDto?>(null)
    val uiState: StateFlow<HPediaDescUiState> = combine(
        _id, _type, termDesc, noteDesc, perfumerDesc
    ) { id, type, term, note, perfumer ->
        if (id == null) {
            throw NullPointerException("Id is NULL")
        }
        when (type) {
            HpediaType.TERM -> {
                HPediaDescUiState.HPediaDesc(
                    title = term?.termTitle ?: "",
                    subTitle = term?.termEnglishTitle ?: "",
                    content = term?.content ?: ""
                )
            }

            HpediaType.NOTE -> {
                HPediaDescUiState.HPediaDesc(
                    title = note?.noteTitle ?: "",
                    subTitle = note?.noteSubtitle ?: "",
                    content = note?.content ?: ""
                )
            }

            HpediaType.PERFUMER -> {
                HPediaDescUiState.HPediaDesc(
                    title = perfumer?.perfumerTitle ?: "",
                    subTitle = perfumer?.perfumerSubTitle ?: "",
                    content = perfumer?.content ?: ""
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
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