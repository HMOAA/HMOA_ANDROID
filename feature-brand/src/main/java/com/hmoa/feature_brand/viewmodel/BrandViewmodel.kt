package com.hmoa.feature_brand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.BrandRepository
import com.hmoa.core_model.data.SortType
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandPerfumeBriefResponseDto
import com.hmoa.feature_brand.PerfumeLikePagingSource
import com.hmoa.feature_brand.PerfumePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandViewmodel @Inject constructor(private val brandRepository: BrandRepository) : ViewModel() {
    private var _sortedLatestPerfumesState = MutableStateFlow<PagingData<BrandPerfumeBriefResponseDto>?>(null)
    val sortedLatestPerfumesState: StateFlow<PagingData<BrandPerfumeBriefResponseDto>?> = _sortedLatestPerfumesState
    private var _sortedLikePerfumesState = MutableStateFlow<PagingData<BrandPerfumeBriefResponseDto>?>(null)
    val sortedLikePerfumesState: StateFlow<PagingData<BrandPerfumeBriefResponseDto>?> = _sortedLikePerfumesState
    private var isSortState = MutableStateFlow(SortType.LATEST)
    private var brandState = MutableStateFlow<BrandDefaultResponseDto?>(null)
    val PAGE_SIZE = 10
    val uiState: StateFlow<BrandUiState> = combine(brandState, isSortState) { brand, isSort ->
        BrandUiState.Data(
            brand = brand,
            sortType = isSort
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BrandUiState.Loading
    )

    fun intializeBrandPerfumes(brandId: Int) {
        viewModelScope.launch {
            flow { emit(brandRepository.getBrand(brandId)) }.asResult().collectLatest {
                when (it) {
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        brandState.value = it.data.data?.data
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    fun latestPerfumesPagingSource(brandId: Int) = PerfumePagingSource(brandRepository, brandId = brandId)

    fun likePerfumesPagingSource(brandId: Int) = PerfumeLikePagingSource(brandRepository, brandId = brandId)

    fun getPagingLatestPerfumes(brandId: Int?): Flow<PagingData<BrandPerfumeBriefResponseDto>>? {
        if (brandId != null) {
            return Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = { latestPerfumesPagingSource(brandId) }
            ).flow.cachedIn(viewModelScope)
        } else {
            return null
        }
    }

    fun getPagingLikePerfumes(brandId: Int?): Flow<PagingData<BrandPerfumeBriefResponseDto>>? {
        if (brandId != null) {
            return Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = { likePerfumesPagingSource(brandId) }
            ).flow.cachedIn(viewModelScope)
        } else {
            return null
        }
    }

    fun onClickSortLike() {
        isSortState.update { SortType.LIKE }
    }

    fun onClickSortLatest() {
        isSortState.update { SortType.LATEST }
    }

    sealed interface BrandUiState {
        data object Loading : BrandUiState
        data class Data(
            var brand: BrandDefaultResponseDto?,
            var sortType: SortType
        ) : BrandUiState

        data object Error : BrandUiState
    }
}