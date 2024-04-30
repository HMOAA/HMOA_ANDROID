package com.hmoa.core_common

sealed interface ErrorUiState {
    data class ErrorData(
        val expiredTokenError: Boolean,
        val wrongTypeTokenError: Boolean,
        val unknownError: Boolean,
        val generalError: Pair<Boolean, String?>
    ) : ErrorUiState

    data object Loading : ErrorUiState
}