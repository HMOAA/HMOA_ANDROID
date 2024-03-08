package com.hmoa.core_network.service

import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.MemberLoginResponseDto
import com.hmoa.core_model.response.TokenResponseDto
import com.hmoa.core_network.HttpClientProvider
import com.hmoa.core_network.service.LoginService
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

@OptIn(InternalAPI::class)
class LoginServiceImpl @Inject constructor(
    private val httpClientProvider: HttpClientProvider
) : LoginService {
    val jsonContentHttpClient = httpClientProvider.getHttpClientWithJsonHeader()

    override suspend fun postOAuth(
        accessToken: OauthLoginRequestDto,
        provider: Provider
    ): MemberLoginResponseDto {
        val response = jsonContentHttpClient.post("/login/oauth2/${provider}") {
            body = accessToken
        }
        return response.body()
    }

    override suspend fun postRemembered(dto: RememberedLoginRequestDto): TokenResponseDto {
        val response = jsonContentHttpClient.post("/login/remembered") {
            body = dto
        }
        return response.body()
    }
}
