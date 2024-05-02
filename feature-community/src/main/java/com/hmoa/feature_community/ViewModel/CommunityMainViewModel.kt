package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.CommunityPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE = 10

@HiltViewModel
class CommunityMainViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {

    //type 정보
    private val _type = MutableStateFlow(Category.추천)
    val type get() = _type.asStateFlow()
    private var _communities = MutableStateFlow<PagingData<CommunityByCategoryResponseDto>?>(null)
    val _enableLoginErrorDialog = MutableStateFlow<Boolean>(false)
    val uiState: StateFlow<CommunityMainUiState> = combine(
        _communities,
        _enableLoginErrorDialog
    ) { communities, isLoginUser ->
        CommunityMainUiState.Community(
            communities, isLoginUser
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunityMainUiState.Loading
    )

    init {
        checkIsWritingAvailable()
    }

    fun communityPagingSource(): Flow<PagingData<CommunityByCategoryResponseDto>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getCommunityPaging(type.value.name)
        }
    ).flow.cachedIn(viewModelScope)

    //category 정보 변경
    fun updateCategory(category: Category) {
        _type.update { category }
        _communities.update { null }
    }

    private fun getCommunityPaging(category: String) = CommunityPagingSource(
        communityRepository = communityRepository,
        category = category
    )

    fun checkIsWritingAvailable() {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.getAuthToken().collectLatest {
                if (it == null) {
                    _enableLoginErrorDialog.update { true }
                } else {
                    _enableLoginErrorDialog.update { false }
                }
            }
        }
    }
}

sealed interface CommunityMainUiState {
    data object Loading : CommunityMainUiState
    data class Community(
        val communities: PagingData<CommunityByCategoryResponseDto>?,
        val enableLoginErrorDialog: Boolean
    ) : CommunityMainUiState

    data object Error : CommunityMainUiState
}