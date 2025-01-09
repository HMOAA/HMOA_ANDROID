package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CommunityHomeViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {
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

    val uiState: StateFlow<CommunityHomeUiState> = flow {
        val result = communityRepository.getCommunitiesHome()
        if (result.errorMessage != null) throw Exception(result.errorMessage!!.message)
        emit(result.data!!)
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> CommunityHomeUiState.Loading
            is Result.Success -> CommunityHomeUiState.Community(result.data.toImmutableList())
            is Result.Error -> {
                handleErrorType(
                    error = result.exception,
                    onExpiredTokenError = {expiredTokenErrorState.update { true }},
                    onWrongTypeTokenError = {wrongTypeTokenErrorState.update { true }},
                    onUnknownError = {unLoginedErrorState.update { true }},
                    onGeneralError = {generalErrorState.update { Pair(true, result.exception.message) }}
                )
                CommunityHomeUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = CommunityHomeUiState.Loading
    )
}

sealed interface CommunityHomeUiState {
    data object Loading : CommunityHomeUiState
    data class Community(
        val communities: kotlinx.collections.immutable.ImmutableList<CommunityByCategoryResponseDto>
    ) : CommunityHomeUiState
    data object Error : CommunityHomeUiState
}