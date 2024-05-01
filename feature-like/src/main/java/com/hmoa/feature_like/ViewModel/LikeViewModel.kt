package com.hmoa.feature_like.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.response.PerfumeLikeResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository
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

    val uiState: StateFlow<LikeUiState> = flow {
        val result = perfumeRepository.getLikePerfumes()
        if (result.errorMessage != null) {
            throw Exception(result.errorMessage?.message)
        }
        emit(result.data)
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> LikeUiState.Loading
            is Result.Success -> {
                LikeUiState.Like(result.data?.data ?: emptyList())
            }

            is Result.Error -> {
                when (result.exception.message) {
                    ErrorMessageType.EXPIRED_TOKEN.message -> {
                        expiredTokenErrorState.update { true }
                    }

                    ErrorMessageType.WRONG_TYPE_TOKEN.message -> {
                        wrongTypeTokenErrorState.update { true }
                    }

                    ErrorMessageType.UNKNOWN_ERROR.message -> {
                        unLoginedErrorState.update { true }
                    }

                    else -> {
                        generalErrorState.update { Pair(true, result.exception.message) }
                    }
                }
                LikeUiState.Error(result.exception.toString())
            }

        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = LikeUiState.Loading
    )

}

sealed interface LikeUiState {
    data object Loading : LikeUiState
    data class Like(
        val perfumes: List<PerfumeLikeResponseDto>
    ) : LikeUiState

    data class Error(
        val message: String
    ) : LikeUiState
}