package com.hmoa.core_network.authentication

import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshTokenManager: RefreshTokenManager
) : okhttp3.Authenticator {
    private var isAvailableToSendNewRequest = false
    private lateinit var newRequest: Request
    override fun authenticate(route: Route?, response: Response): Request? {
        val rememberedToken = runBlocking {
            tokenManager.getRememberedToken().first()
        }

        if (rememberedToken == null) {
            response.close()
            return null
        }
        runBlocking {
            refreshTokenManager.refreshTokens(RememberedLoginRequestDto(rememberedToken))
                .suspendOnError {
                    if (this.response.code() == 401) {
                        isAvailableToSendNewRequest = false
                    }
                }
                .suspendOnSuccess {
                    if (this.response.body() != null) {
                        val refreshedAuthToken = tokenManager.getAuthToken().firstOrNull()
                        newRequest = response.request.addRefreshAuthToken(refreshedAuthToken)
                        isAvailableToSendNewRequest = true
                    }
                }
        }

        if (isAvailableToSendNewRequest) {
            return newRequest
        }
        return null
    }

    fun Request.addRefreshAuthToken(token: String?): Request {
        return this.newBuilder().header("X-AUTH-TOKEN", "${token}").build()
    }
}
