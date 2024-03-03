package com.hmoa.core_network.authentication

import com.hmoa.core_database.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val rememberedToken = runBlocking {
            tokenManager.getRememberedToken().first()
        }

        if (rememberedToken == null) {
            response.close()
            return null
        }

        val refreshedAuthToken = suspend { //TODO("토큰리프레시 재요청 필요 RefreshTokenService에서 할 예정")}
            //val refreshedRequest = response.request.addRefreshAuthToken(refreshedAuthToken.toString())
            //return refreshedRequest
        }
        return null
    }

    fun Request.addRefreshAuthToken(token: String): Request {
        return this.newBuilder().header("X-AUTH-TOKEN", "${token}").build()
    }
}
