package com.hmoa.core_network.authentication

import android.util.Log
import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        CoroutineScope(Dispatchers.IO).launch {
            refreshTokenManager.refreshTokens(RememberedLoginRequestDto(rememberedToken))
                .suspendOnError {
                    if (this.response.code() == 401) {
                        isAvailableToSendNewRequest = false
                        Log.e("AuthAuthenticator", "토큰 리프레싱 실패")
                    }
                }
                .suspendOnSuccess {
                    if (this.response.body() != null) {
                        val refreshedAuthToken = this.response.body()!!.authToken
                        val refreshedRememberToken = this.response.body()!!.rememberedToken
                        refreshTokenManager.saveRefreshTokens(refreshedAuthToken, refreshedRememberToken)
                        newRequest = response.request.addRefreshAuthToken(refreshedAuthToken)
                        isAvailableToSendNewRequest = true
                        Log.d("AuthAuthenticator", "토큰 리프레싱 성공")
                    }
                }
        }

        if (isAvailableToSendNewRequest) {
            isAvailableToSendNewRequest = false
            return newRequest
        }
        return null
    }

    fun Request.addRefreshAuthToken(token: String?): Request {
        return this.newBuilder().header("X-AUTH-TOKEN", "${token}").build()
    }
}
