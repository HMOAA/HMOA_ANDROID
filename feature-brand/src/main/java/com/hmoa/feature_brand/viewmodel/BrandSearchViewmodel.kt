package com.hmoa.feature_brand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.feature_brand.BrandUiState
import com.hmoa.feature_brand.ConsonantBrandsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BrandSearchViewmodel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {
    val PAGE_SIZE = 10
    private var brandState = MutableStateFlow<BrandDefaultResponseDto?>(null)
    private var isSortState = MutableStateFlow(null)
    var uiState: StateFlow<BrandUiState> = combine(brandState, isSortState) { brand, isSort ->
        BrandUiState.Data(
            brand = brand,
            sortType = isSort
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BrandUiState.Loading
    )

    fun getConsonantBrandsPagingSource(): Flow<PagingData<BrandDefaultResponseDto>>? {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { ConsonantBrandsPagingSource(searchRepository) }
        ).flow.cachedIn(viewModelScope)
    }

    fun initializeBrands() {
        
    }

}