package com.hmoa.core_domain

import ResultResponse
import android.util.Log

suspend fun <T> ResultResponse<T>.emitOrThrow(emit: suspend (ResultResponse<T>) -> Unit) {
    Log.d("core-domain/ErrorHandleFunctions", "emitOrThrow --- data: ${data}, errorMessage: ${errorMessage?.message}")
    if (this.data != null) {
        emit(this)
    } else if (this.errorMessage != null) {
        throw Exception(this.errorMessage!!.message)
    } else { //모두 Null인 경우
        throw Exception("알 수 없는 에러입니다 :(")
    }
}