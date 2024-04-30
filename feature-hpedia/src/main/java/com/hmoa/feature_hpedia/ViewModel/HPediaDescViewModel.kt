package com.hmoa.feature_hpedia.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.NoteRepository
import com.hmoa.core_domain.repository.PerfumerRepository
import com.hmoa.core_domain.repository.TermRepository
import com.hmoa.core_model.response.NoteDescResponseDto
import com.hmoa.core_model.response.PerfumerDescResponseDto
import com.hmoa.core_model.response.TermDescResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HPediaDescViewModel @Inject constructor(
    private val termRepository: TermRepository,
    private val noteRepository: NoteRepository,
    private val perfumerRepository: PerfumerRepository
) : ViewModel() {

    private val id = MutableStateFlow<Int?>(null)

    private val _type = MutableStateFlow<String?>(null)
    val type get() = _type.asStateFlow()

    private val _errState = MutableStateFlow<String?>(null)
    val errState get() = _errState.asStateFlow()

    val uiState: StateFlow<HPediaDescUiState> = id.combine(
        _type
    ) { id, type ->
        if (id == null || type == null) {
            throw NullPointerException("Id or Type is NULL")
        }
        when (type) {
            "용어" -> {
                val result = termRepository.getTerm(id)
                if (result.errorMessage != null) {
                    throw Exception(result.errorMessage!!.message)
                }
                result.data!!
            }

            "노트" -> {
                val result = noteRepository.getNote(id)
                if (result.errorMessage != null) {
                    throw Exception(result.errorMessage!!.message)
                }
                result.data!!
            }

            "조향사" -> {
                val result = perfumerRepository.getPerfumer(id)
                if (result.errorMessage != null) {
                    throw Exception(result.errorMessage!!.message)
                }
                result.data!!
            }

            else -> {
                throw IllegalArgumentException("Not Exist Type")
            }
        }
    }.asResult().map { result ->
        when (result) {
            is Result.Error -> {
                HPediaDescUiState.Error
            }

            is Result.Success -> {
                val data = result.data
                when (type.value) {
                    "용어" -> {
                        val term = data.data as TermDescResponseDto
                        HPediaDescUiState.HPediaDesc(
                            title = term.termTitle,
                            subTitle = term.termEnglishTitle,
                            content = term.content
                        )
                    }

                    "노트" -> {
                        val note = result.data as NoteDescResponseDto
                        HPediaDescUiState.HPediaDesc(
                            title = note.noteTitle,
                            subTitle = note.noteSubtitle,
                            content = note.content
                        )
                    }

                    "조향사" -> {
                        val perfumer = result.data as PerfumerDescResponseDto
                        HPediaDescUiState.HPediaDesc(
                            title = perfumer.perfumerTitle,
                            subTitle = perfumer.perfumerSubTitle,
                            content = perfumer.content
                        )
                    }

                    else -> {
                        HPediaDescUiState.Error
                    }
                }
            }

            is Result.Loading -> {
                HPediaDescUiState.Loading
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = HPediaDescUiState.Loading
    )

    fun setInfo(type: String?, id: Int?) {
        this._type.update { type }
        this.id.update { id }
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