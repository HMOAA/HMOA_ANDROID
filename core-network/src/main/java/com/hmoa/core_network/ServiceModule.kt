package com.hmoa.core_network

import com.hmoa.core_network.Fcm.FcmService
import com.hmoa.core_network.Fcm.FcmServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {

        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
//                url(TODO("util모듈 함수로 core-network-secret.json에서 값 추출해서 붙이기"))
                header(HttpHeaders.ContentType, ContentType.Application.Json)
//                header("X-AUTH-TOKEN", TODO("authentication 모듈에서 토큰을 주입해야 함"))
            }
            install(ContentNegotiation) {
                json()
            }
            install(HttpCache) {
//                TODO("캐쉬 추가 설정 필요")
            }
        }
    }

    @Singleton
    @Provides
    fun providePerfumeService(httpClient: HttpClient): PerfumeService = PerfumeServiceImpl(httpClient)

    @Singleton
    @Provides
    fun providerFcmService(httpClient : HttpClient) : FcmService = FcmServiceImpl(httpClient)
}