package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.CommunityPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val PAGE_SIZE = 10

@HiltViewModel
class CommunityMainViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {

    //type 정보
    private val _type = MutableStateFlow(Category.추천)
    val type get() = _type.asStateFlow()

    private val _errState = MutableStateFlow("")
    val errState get() = _errState.asStateFlow()

    val uiState : StateFlow<CommunityMainUiState> = combine(type){
        Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {getCommunityPaging(type.value.name)}
        ).flow.cachedIn(viewModelScope)
    }.asResult().map{
        when (it) {
            is Result.Loading -> CommunityMainUiState.Loading
            is Result.Success -> {
                CommunityMainUiState.Community(it.data)
            }
            is Result.Error -> CommunityMainUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunityMainUiState.Loading
    )

    //category 정보 변경
    fun updateCategory(category: Category) {
        _type.update { category }
    }

    private fun getCommunityPaging(category : String) = CommunityPagingSource(
        communityRepository = communityRepository,
        category = category
    )
}

sealed interface CommunityMainUiState {
    data object Loading : CommunityMainUiState
    data class Community(
        val communities: Flow<PagingData<CommunityByCategoryResponseDto>>
    ) : CommunityMainUiState

    data object Error : CommunityMainUiState
}