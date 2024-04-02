package com.hmoa.core_network.authentication

import com.google.gson.GsonBuilder
import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.TokenResponseDto
import com.hmoa.core_network.BuildConfig
import com.hmoa.core_network.service.LoginService
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefreshTokenManagerImpl @Inject constructor(private val tokenManager: TokenManager) : RefreshTokenManager {

    override suspend fun refreshTokens(dto: RememberedLoginRequestDto): ApiResponse<TokenResponseDto> {
        val response = createWebService().create(LoginService::class.java).postRemembered(dto)
        var refreshedAuthToken = ""
        var rememberedToken = ""
        response.suspendOnSuccess {
            refreshedAuthToken = this.response.body()!!.authToken
            rememberedToken = this.response.body()!!.rememberedToken
        }

        CoroutineScope(Dispatchers.IO).async {
            saveRefreshTokens(refreshedAuthToken, rememberedToken)
        }
        return response
    }

    override suspend fun saveRefreshTokens(authToken: String, rememberedToken: String) {
        tokenManager.saveAuthToken(authToken)
        tokenManager.saveRememberedToken(rememberedToken)
    }


    private val okHttp =
        OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()

    private fun createWebService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttp)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}