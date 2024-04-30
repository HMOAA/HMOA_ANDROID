package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CommunitySearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    //검색어
    private val _searchWord = MutableStateFlow("")
    val searchWord get() = _searchWord.asStateFlow()

    //flag
    private val _flag = MutableStateFlow(false)

    val uiState: StateFlow<CommunitySearchUiState> = combine(
        _searchWord,
        _flag
    ) { word, flag ->
        if (_searchWord.value.isEmpty()) {
            return@combine emptyList()
        }
        val result = searchRepository.getCommunity(0, word)
        if (result.errorMessage != null) {
            throw Exception(result.errorMessage!!.message)
        }
        result.data!!
    }.asResult().map {
        when (it) {
            is Result.Loading -> {
                CommunitySearchUiState.Loading
            }

            is Result.Success -> {
                CommunitySearchUiState.SearchResult(it.data)
            }

            is Result.Error -> {
                CommunitySearchUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunitySearchUiState.Loading
    )

    //검색어 변동 함수
    fun updateSearchWord(newSearchWord: String) {
        _searchWord.update { newSearchWord }
    }

    //검색어 리셋 함수
    fun clearSearchWord() {
        _searchWord.update { "" }
    }

    //flag update
    fun updateFlag() {
        _flag.update { !_flag.value }
    }

}

sealed interface CommunitySearchUiState {
    data object Loading : CommunitySearchUiState
    data class SearchResult(
        val result: List<CommunityByCategoryResponseDto>
    ) : CommunitySearchUiState

    data object Error : CommunitySearchUiState
}