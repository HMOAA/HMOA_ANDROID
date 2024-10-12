package com.hmoa.core_network.authentication

import com.hmoa.core_model.data.ErrorMessage

interface Authenticator {
    // API 에러를 처리하고, 토큰 갱신이 필요한 경우 처리하는 함수
    suspend fun handleApiError(
        rawMessage: String,
        handleErrorMesssage: (i: ErrorMessage) -> Unit,
        onCompleteTokenRefresh: suspend () -> Unit
    )

    // 토큰 갱신 처리 함수
    suspend fun onRefreshToken(
        onRefreshSuccess: suspend () -> Unit,
        onRefreshFail: (errorMessage: ErrorMessage) -> Unit
    )

    // 모든 토큰을 업데이트하는 함수
    suspend fun updateAllTokens(authToken: String, rememberToken: String)
}