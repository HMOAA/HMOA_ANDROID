package com.hmoa.core_network.authentication

import com.hmoa.core_database.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken = runBlocking {
            tokenManager.getAuthToken().first()
        } ?: return errorResponse(request = chain.request())

        val request = getRequestWithAuthToken(chain, authToken)
        val response = chain.proceed(request)

        return response
    }

    fun getRequestWithAuthToken(chain: Interceptor.Chain, token: String): Request {
        return chain.request().newBuilder().header("X-AUTH-TOKEN", "${token}").build()
    }

    fun errorResponse(request: Request): Response = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(401)
        .message("")
        .body(ResponseBody.create(null, ""))
        .build()

}