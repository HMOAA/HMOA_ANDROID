package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
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

    private var _communities = MutableStateFlow<PagingData<CommunityByCategoryResponseDto>?>(null)

    private val _errState = MutableStateFlow("")
    val errState get() = _errState.asStateFlow()

    val uiState : StateFlow<CommunityMainUiState> = combine(
        _communities,
        type
    ) { communities, type ->
        CommunityMainUiState.Community(
            communities
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunityMainUiState.Loading
    )

    fun communityPagingSource() : Flow<PagingData<CommunityByCategoryResponseDto>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getCommunityPaging(type.value.name)
        }
    ).flow.cachedIn(viewModelScope)

    //category 정보 변경
    fun updateCategory(category: Category) {
        _type.update { category }
        _communities.update{ null }
    }

    private fun getCommunityPaging(category : String) = CommunityPagingSource(
        communityRepository = communityRepository,
        category = category
    )
}

sealed interface CommunityMainUiState {
    data object Loading : CommunityMainUiState
    data class Community(
        val communities: PagingData<CommunityByCategoryResponseDto>?
    ) : CommunityMainUiState

    data object Error : CommunityMainUiState
}