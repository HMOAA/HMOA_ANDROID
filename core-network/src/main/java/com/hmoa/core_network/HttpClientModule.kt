package com.hmoa.core_network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.hmoa.core_database.TokenManager
import com.hmoa.core_repository.Login.LoginRepository
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
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HttpClientModule {
    @Provides
    private fun provideBaseUrl(): String = dotenv().get("BASE_URL")

    @Provides
    @Singleton
    fun provideTokenManager(dataStore: DataStore<Preferences>): TokenManager {
        return TokenManager(dataStore)
    }

    @Provides
    private fun provideInterceptor(tokenManager: TokenManager, loginRepository: LoginRepository): AuthInterceptor =
        AuthInterceptor(tokenManager, loginRepository)

    @Provides
    private fun provideOkHttp(interceptor: Interceptor): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(interceptor)
            .protocols(mutableListOf(Protocol.HTTP_2))
            .build()

        return httpBuilder
    }

    @Provides
    private fun provideHttpClient(
        baseUrl: String,
        authToken: String,
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
                    host = baseUrl
                }
            }
        }
    }
}