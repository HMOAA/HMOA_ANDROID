package com.hyangmoa.core_common

sealed interface ErrorUiState {
    data class ErrorData(
        val expiredTokenError: Boolean,
        val wrongTypeTokenError: Boolean,
        val unknownError: Boolean,
        val memberNotFoundError: Boolean? = null,
        val generalError: Pair<Boolean, String?>
    ) : ErrorUiState

    data object Loading : ErrorUiState
}