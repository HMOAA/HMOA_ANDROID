package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
    }.asResult().map { result ->
        when (result) {
            is Result.Loading -> CommunitySearchUiState.Loading
            is Result.Success -> CommunitySearchUiState.SearchResult(result.data)
            is Result.Error -> {
                generalErrorState.update{ Pair(true, result.exception.message)}
                CommunitySearchUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunitySearchUiState.Loading
    )

    //검색어 변동 함수
    fun updateSearchWord(newSearchWord: String) = _searchWord.update { newSearchWord }
    //검색어 리셋 함수
    fun clearSearchWord() = _searchWord.update { "" }
    //flag update
    fun updateFlag() = _flag.update { !_flag.value }
}

sealed interface CommunitySearchUiState {
    data object Loading : CommunitySearchUiState
    data class SearchResult(
        val result: List<CommunityByCategoryResponseDto>
    ) : CommunitySearchUiState

    data object Error : CommunitySearchUiState
}