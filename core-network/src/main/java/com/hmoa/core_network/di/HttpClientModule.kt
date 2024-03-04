package com.hmoa.core_network.di

import com.hmoa.core_network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    fun provideOkHttp(interceptor: Interceptor, authenticator: Authenticator): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .authenticator(authenticator)
            .protocols(mutableListOf(Protocol.HTTP_1_1))
            .build()

        return httpBuilder
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        okHttpClient: OkHttpClient
    ): io.ktor.client.HttpClient {
        return HttpClient(OkHttp) {
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
            }
        }
    }
}