package com.hmoa.core_network

import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_repository.Login.LoginRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val loginRepository: LoginRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken = suspend { loginRepository.getAuthToken() }
        val request = getRequestWithAuthToken(chain, authToken)
        val response = chain.proceed(request)

        if (response.code == 401) {
            //TODO("자동로그인 시도까지 실패한경우(rememberedToken이 만료된 경우) 처리는 보류")
            val rememberedToken = suspend { loginRepository.getRememberedToken() }
            suspend {
                loginRepository.postRemembered(RememberedLoginRequestDto { rememberedToken })
            }
            val newAuthToken = suspend { loginRepository.getAuthToken() }
            val newRequest = getRequestWithAuthToken(chain, newAuthToken)
            return chain.proceed(newRequest)
        }

        return response
    }

    fun getRequestWithAuthToken(chain: Interceptor.Chain, token: suspend () -> String?): Request {
        return chain.request().newBuilder().header("X-AUTH-TOKEN", "${token}").build()
    }

}