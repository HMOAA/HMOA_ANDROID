package com.hmoa.core_common

sealed interface ErrorUiState {
    data class ErrorData(
        val expiredTokenError: Boolean,
        val wrongTypeTokenError: Boolean,
        val unknownError: Boolean,
        val memberNotFoundError: Boolean? = null,
        val generalError: Pair<Boolean, String?>
    ) : ErrorUiState {
        fun isValidate(): Boolean{
            return expiredTokenError || wrongTypeTokenError || unknownError || generalError.first
        }
    }

    data object Loading : ErrorUiState
}