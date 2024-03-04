package com.hmoa.core_network.authentication

import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.request.RememberedLoginRequestDto
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshTokenManager: RefreshTokenManager
) : Authenticator {
    private lateinit var refreshAuthTokenResponse: HttpResponse
    private lateinit var newRequest: Request
    override fun authenticate(route: Route?, response: Response): Request? {
        val rememberedToken = runBlocking {
            tokenManager.getRememberedToken().first()
        }

        if (rememberedToken == null) {
            response.close()
            return null
        }

        //함수화 해야함. 토큰 리프레싱 요청한 뒤 갱신된 토큰으로 재요청 보내는 것.
        runBlocking {
            refreshAuthTokenResponse = flow {
                emit(refreshTokenManager.refreshAuthToken(RememberedLoginRequestDto(rememberedToken)))
            }.first()

        }

        if (refreshAuthTokenResponse.status.value == HttpStatusCode.Unauthorized.value) {
            //TODO("로그인 화면으로 이동")
            return null
        }

        CoroutineScope(Dispatchers.IO).async {
            val refreshedAuthToken = tokenManager.getAuthToken().first() ?: return@async null
            newRequest = response.request.addRefreshAuthToken(refreshedAuthToken)
        }

        return newRequest
    }


    fun Request.addRefreshAuthToken(token: String): Request {
        return this.newBuilder().header("X-AUTH-TOKEN", "${token}").build()
    }
}
