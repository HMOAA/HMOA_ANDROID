package com.hmoa.core_network.service

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

@Module
@InstallIn(SingletonComponent::class)
class HttpClientModule {
    @Provides
    private fun provideBaseUrl(): String = dotenv().get("BASE_URL")

    @Provides
    private fun provideAuthToken(): String = TODO("core-database에서 저장된 토큰을 받아와야 함")

    @Provides
    private fun provideHttpClient(baseUrl: String, authToken: String): io.ktor.client.HttpClient {
        return HttpClient(OkHttp) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
            defaultRequest {
                headers {
                    append("X-AUTH-TOKEN", "${authToken}")
                }
                url {
                    protocol = URLProtocol.HTTPS
                    host = baseUrl
                }
            }
        }
    }
}