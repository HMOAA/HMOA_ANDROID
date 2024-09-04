package com.hmoa.core_common

import ResultResponse

suspend fun <T> ResultResponse<T>.emitOrThrow(emit: suspend (ResultResponse<T>) -> Unit) {
    if (this.data != null) {
        emit(this)
    } else if (this.errorMessage != null) {
        throw Exception(this.errorMessage!!.message)
    }
}

fun handleErrorType(
    error: Throwable,
    onExpiredTokenError: () -> Unit,
    onWrongTypeTokenError: () -> Unit,
    onUnknownError: () -> Unit,
    onGeneralError: () -> Unit
) {
    when (error.message) {
        ErrorMessageType.EXPIRED_TOKEN.message -> onExpiredTokenError()

        ErrorMessageType.WRONG_TYPE_TOKEN.message -> onWrongTypeTokenError()

        ErrorMessageType.UNKNOWN_ERROR.message -> onUnknownError()

        else -> onGeneralError()
    }
}