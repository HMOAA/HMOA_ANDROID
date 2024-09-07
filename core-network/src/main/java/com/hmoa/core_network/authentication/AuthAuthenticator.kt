package com.hmoa.core_network.authentication

import android.util.Log
import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshTokenManager: RefreshTokenManager
) : okhttp3.Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val rememberedToken = runBlocking {
            tokenManager.getRememberedToken().first()
        }

        if (rememberedToken == null) {
            response.close()
            return null
        }

        var newRequest: Request? = null
        runBlocking {
            refreshTokenManager.refreshTokens(RememberedLoginRequestDto(rememberedToken))
                .suspendOnError {
                    if (this.response.code() == 401) {
                        Log.e("AuthAuthenticator", "토큰 리프레싱 실패")
                    }
                }
                .suspendOnSuccess {
                    val responseBody = this.response.body()
                    if (responseBody != null) {
                        val refreshedAuthToken = responseBody.authToken
                        val refreshedRememberToken = responseBody.rememberedToken
                        launch {
                            tokenManager.deleteAuthToken()
                            tokenManager.deleteRememberedToken()
                        }.join()
                        refreshTokenManager.saveRefreshTokens(refreshedAuthToken, refreshedRememberToken)
                        newRequest = response.request.addRefreshAuthToken(refreshedAuthToken)
                        Log.d("AuthAuthenticator", "토큰 리프레싱 성공")
                    }
                }
        }

        response.close()
        return newRequest
    }

    fun Request.addRefreshAuthToken(token: String?): Request {
        return this.newBuilder().header("X-AUTH-TOKEN", "${token}").build()
    }
}
