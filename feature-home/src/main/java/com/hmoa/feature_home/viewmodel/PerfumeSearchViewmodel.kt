package com.hmoa.feature_home.viewmodel

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.response.PerfumeNameSearchResponseDto
import com.hmoa.feature_home.PerfumeNameSearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PerfumeSearchViewmodel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {
    private var _searchWordState = MutableStateFlow<String?>(null)
    val searchWordState: StateFlow<String?> = _searchWordState

    fun perfumeNameSearchPagingSource(word: String) = PerfumeNameSearchPagingSource(searchRepository, word)

    fun getPagingPerfumeSearchResults(): Flow<PagingData<PerfumeNameSearchResponseDto>>? {
        if (_searchWordState.value != null) {
            return Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = { perfumeNameSearchPagingSource(_searchWordState.value!!) }
            ).flow.cachedIn(viewModelScope)
        }
        return null
    }

    fun updateSearchWord(word: String) {
        _searchWordState.update { word }
    }
}