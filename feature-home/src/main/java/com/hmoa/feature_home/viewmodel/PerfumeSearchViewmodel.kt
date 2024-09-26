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
import com.hmoa.core_model.response.PerfumeSearchResponseDto
import com.hmoa.feature_home.PerfumeNameSearchPagingSource
import com.hmoa.feature_home.PerfumeSearchPagingSource
import com.hmoa.core_domain.entity.data.PerfumeSearchViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PerfumeSearchViewmodel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {
    private var _perfumeNameSearchWordState = MutableStateFlow<String?>(null)
    val perfumeNameSearchWordState: StateFlow<String?> = _perfumeNameSearchWordState
    private var _perfumeSearchWordState = MutableStateFlow<String?>(null)
    val perfumeSearchWordState: StateFlow<String?> = _perfumeSearchWordState
    private var _searchResultViewType = MutableStateFlow<PerfumeSearchViewType>(
        PerfumeSearchViewType.List)
    val searchResultViewType: StateFlow<PerfumeSearchViewType> = _searchResultViewType

    fun perfumeNameSearchPagingSource(word: String) = PerfumeNameSearchPagingSource(searchRepository, word)

    fun perfumeSearchPagingSource(word: String) = PerfumeSearchPagingSource(searchRepository, word)

    fun getPagingPerfumeNameSearchResults(): Flow<PagingData<PerfumeNameSearchResponseDto>>? {
        if (_perfumeNameSearchWordState.value != null) {
            return Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = { perfumeNameSearchPagingSource(_perfumeNameSearchWordState.value!!) }
            ).flow.cachedIn(viewModelScope)
        }
        return null
    }

    fun getPagingPerfumeSearchResults(): Flow<PagingData<PerfumeSearchResponseDto>>? {
        if (_perfumeSearchWordState.value != null) {
            return Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = { perfumeSearchPagingSource(_perfumeSearchWordState.value!!) }
            ).flow.cachedIn(viewModelScope)
        }
        return null
    }

    fun updatePerfumeNameSearchWord(word: String) {
        _perfumeNameSearchWordState.update { word }
    }

    fun updatePerfumeSearchWord(word: String) {
        _perfumeSearchWordState.update { word }
    }

    fun changeViewType(viewType: PerfumeSearchViewType) {
        _searchResultViewType.update { viewType }
    }
}