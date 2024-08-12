package com.hyangmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hyangmoa.core_common.ErrorUiState
import com.hyangmoa.core_domain.repository.CommunityRepository
import com.hyangmoa.core_domain.repository.LoginRepository
import com.hyangmoa.core_model.Category
import com.hyangmoa.core_model.response.CommunityByCategoryResponseDto
import com.hyangmoa.feature_community.CommunityPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE = 10

@HiltViewModel
class CommunityMainViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val authToken = MutableStateFlow<String?>(null)
    //type 정보
    private val _type = MutableStateFlow(Category.추천)
    val type get() = _type.asStateFlow()
    private var _communities = MutableStateFlow<PagingData<CommunityByCategoryResponseDto>?>(null)
    val _enableLoginErrorDialog = MutableStateFlow<Boolean>(false)

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
    init {getAuthToken()}

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
    //err state update
    fun updateLoginError(){unLoginedErrorState.update{true}}
    fun hasToken() = authToken.value != null
    //get token
    private fun getAuthToken() {
        viewModelScope.launch {
            loginRepository.getAuthToken().onEmpty { }.collectLatest {authToken.value = it}
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