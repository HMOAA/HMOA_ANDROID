package com.hmoa.core_network

import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_network.authentication.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authenticator: Authenticator
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken = suspend { authenticator.getAuthToken() }
        val request = getRequestWithAuthToken(chain, authToken)
        val response = chain.proceed(request)

        if (response.code == 401) {
            //TODO("자동로그인 시도까지 실패한경우(rememberedToken이 만료된 경우) 처리는 보류")
            val rememberedToken = suspend { authenticator.getRememberedToken() }
            suspend {
                authenticator.postRemembered(RememberedLoginRequestDto { rememberedToken })
            }
            val newAuthToken = suspend { authenticator.getAuthToken() }
            val newRequest = getRequestWithAuthToken(chain, newAuthToken)
            val newResponse = chain.proceed(newRequest)

            if (newResponse.code == 401) {
                //TODO("로그인 화면으로 이동필요")
            }
        }

        return response
    }

    fun getRequestWithAuthToken(chain: Interceptor.Chain, token: suspend () -> String?): Request {
        return chain.request().newBuilder().header("X-AUTH-TOKEN", "${token}").build()
    }

}