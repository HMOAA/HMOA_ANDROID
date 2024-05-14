package com.hmoa.core_network.authentication

import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.TokenResponseDto
import com.hmoa.core_network.BuildConfig
import com.hmoa.core_network.service.LoginService
import com.skydoves.sandwich.ApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefreshTokenManagerImpl @Inject constructor(private val tokenManager: TokenManager) : RefreshTokenManager {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    private val okHttp =
        OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS).build()


    override suspend fun refreshTokens(dto: RememberedLoginRequestDto): ApiResponse<TokenResponseDto> {
        val response =
            createWebService(BuildConfig.BASE_URL, okHttp).create(LoginService::class.java).postRemembered(dto)
        return response
    }

    override suspend fun saveRefreshTokens(authToken: String, rememberedToken: String) {
        tokenManager.saveAuthToken(authToken)
        tokenManager.saveRememberedToken(rememberedToken)
    }
}