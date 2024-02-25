package com.hmoa.core_network

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.http.*
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class HttpClientProvider @Inject constructor(private val httpClient: HttpClient) {
    fun getHttpClientWithJsonHeader(): HttpClient {
        return httpClient.config {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    fun getHttpClientWithFormUrlEncodedHeader(): HttpClient {
        return httpClient.config {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
            }
        }
    }
}