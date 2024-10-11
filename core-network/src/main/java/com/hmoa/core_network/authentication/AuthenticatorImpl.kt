package com.hmoa.core_network.authentication

import android.util.Log
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Inject


class AuthenticatorImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshTokenManager: RefreshTokenManager
) : Authenticator {
    override suspend fun handleApiError(
        rawMessage: String,
        handleErrorMesssage: (i: ErrorMessage) -> Unit,
        onCompleteTokenRefresh: suspend () -> Unit
    ) {
        Json.decodeFromString<ErrorMessage>(rawMessage).apply {
            onTokenRefresh {
                onRefreshToken(
                    onRefreshSuccess = {
                        onCompleteTokenRefresh()
                    },
                    onRefreshFail = { handleErrorMesssage(this) }
                )
            }
            onHandleError {
                handleErrorMesssage(this)
            }
        }
    }

    override suspend fun onRefreshToken(
        onRefreshSuccess: suspend () -> Unit,
        onRefreshFail: (errorMessage: ErrorMessage) -> Unit
    ) {
        val rememberedToken = runBlocking {
            tokenManager.getRememberedToken().first()
        }
        if (rememberedToken != null) {
            refreshTokenManager.refreshTokens(RememberedLoginRequestDto(rememberedToken))
                .suspendOnError {
                    if (this.response.code() == 404) {
                        Log.e("AuthAuthenticator", "토큰 리프레싱 실패")
                        onRefreshFail(Json.decodeFromString<ErrorMessage>(this.message()))
                    }
                }
                .suspendOnSuccess {
                    val responseBody = this.response.body()
                    if (responseBody != null) {
                        Log.d("AuthAuthenticator", "토큰 리프레싱 성공")
                        updateAllTokens(
                            authToken = responseBody.authToken,
                            rememberToken = responseBody.rememberedToken
                        )
                        onRefreshSuccess()
                    }
                }
        }
    }

    override suspend fun updateAllTokens(authToken: String, rememberToken: String) {
        tokenManager.deleteAuthToken()
        tokenManager.deleteRememberedToken()
        refreshTokenManager.saveRefreshTokens(authToken, rememberToken)
    }

    suspend inline fun ErrorMessage.onTokenRefresh(
        crossinline onResult: suspend ErrorMessage.() -> Unit,
    ): ErrorMessage {
        if (this.message == ErrorMessageType.EXPIRED_TOKEN.message) {
            onResult(this)
        }
        return this
    }

    suspend inline fun ErrorMessage.onHandleError(
        crossinline onResult: suspend ErrorMessage.() -> Unit,
    ): ErrorMessage {
        if (this.message != ErrorMessageType.EXPIRED_TOKEN.message) {
            onResult(this)
        }
        return this
    }
}