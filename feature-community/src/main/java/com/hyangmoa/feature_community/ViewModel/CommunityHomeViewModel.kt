package com.hyangmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyangmoa.core_common.ErrorMessageType
import com.hyangmoa.core_common.ErrorUiState
import com.hyangmoa.core_common.Result
import com.hyangmoa.core_common.asResult
import com.hyangmoa.core_domain.repository.CommunityRepository
import com.hyangmoa.core_domain.repository.LoginRepository
import com.hyangmoa.core_model.response.CommunityByCategoryResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityHomeViewModel @Inject constructor(
    private val communityRepository: CommunityRepository,
    private val loginRepository: LoginRepository,
) : ViewModel() {
    private val authToken = MutableStateFlow<String?>(null)

    private val _community = MutableStateFlow(emptyList<CommunityByCategoryResponseDto>())
    val community get() = _community.asStateFlow()

    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var memberNotFoundErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorUiState: StateFlow<ErrorUiState> = combine(
        expiredTokenErrorState,
        wrongTypeTokenErrorState,
        unLoginedErrorState,
        memberNotFoundErrorState,
        generalErrorState
    ) { expiredTokenError, wrongTypeTokenError, unknownError, memberNotFoundError, generalError ->
        ErrorUiState.ErrorData(
            expiredTokenError = expiredTokenError,
            wrongTypeTokenError = wrongTypeTokenError,
            unknownError = unknownError,
            memberNotFoundError = memberNotFoundError,
            generalError = generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )

    init {
        getAuthToken()
    }

    val uiState: StateFlow<CommunityHomeUiState> = flow {
        val result = communityRepository.getCommunitiesHome()
        if (result.errorMessage != null) {
            throw Exception(result.errorMessage!!.message)
        }
        emit(result.data!!)
    }.asResult()
        .map { result ->
            when (result) {
                is Result.Loading -> CommunityHomeUiState.Loading
                is Result.Success -> CommunityHomeUiState.Community(result.data)
                is Result.Error -> {
                    when (result.exception.message) {
                        ErrorMessageType.EXPIRED_TOKEN.message -> expiredTokenErrorState.update { true }
                        ErrorMessageType.WRONG_TYPE_TOKEN.message -> wrongTypeTokenErrorState.update { true }
                        ErrorMessageType.UNKNOWN_ERROR.message -> unLoginedErrorState.update { true }
                        ErrorMessageType.MEMBER_NOT_FOUND.message -> memberNotFoundErrorState.update { true }
                        else -> generalErrorState.update { Pair(true, result.exception.message) }
                    }
                    CommunityHomeUiState.Error
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1_000),
            initialValue = CommunityHomeUiState.Loading
        )

    fun hasToken(): Boolean = authToken.value != null

    // get token
    private fun getAuthToken() {
        viewModelScope.launch {
            loginRepository.getAuthToken().onEmpty { }.collectLatest {
                authToken.value = it
            }
        }
    }
}

sealed interface CommunityHomeUiState {
    data object Loading : CommunityHomeUiState
    data class Community(
        val communities: List<CommunityByCategoryResponseDto>
    ) : CommunityHomeUiState

    data object Error : CommunityHomeUiState
}