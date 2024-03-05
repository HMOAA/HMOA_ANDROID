package com.hmoa.core_network.authentication

import com.hmoa.core_database.TokenManager
import com.hmoa.core_model.request.RememberedLoginRequestDto
import com.hmoa.core_model.response.TokenResponseDto
import com.hmoa.core_network.BuildConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import okhttp3.OkHttpClient
import okhttp3.Protocol
import javax.inject.Inject

class RefreshTokenManagerImpl @Inject constructor(private val tokenManager: TokenManager) : RefreshTokenManager {

    companion object {
        val okHttpClient = OkHttpClient.Builder()
            .protocols(mutableListOf(Protocol.HTTP_1_1))
            .build()
        val httpClient = HttpClient(OkHttp) {
            engine {
                config {
                    okHttpClient
                }
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BuildConfig.BASE_URL
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun refreshAuthToken(dto: RememberedLoginRequestDto): HttpResponse {
        val response = httpClient.post("/login/remembered") {
            body = dto
        }
        val refreshedAuthToken = response.body<TokenResponseDto>().authToken
        val rememberedToken = response.body<TokenResponseDto>().rememberedToken

        suspend {
            tokenManager.saveAuthToken(refreshedAuthToken)
            tokenManager.saveRememberedToken(rememberedToken)
        }


        return response
    }
}