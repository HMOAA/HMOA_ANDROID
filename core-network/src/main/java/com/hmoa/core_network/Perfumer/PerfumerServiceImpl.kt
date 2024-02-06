package com.hmoa.core_network.Perfumer

import com.hmoa.core_model.response.DataResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class PerfumerServiceImpl @Inject constructor(private val httpClient: HttpClient) : PerfumerService {
    override suspend fun getPerfumers(pageNum: String): DataResponseDto<Any> {
        return httpClient.get("/perfumer") {
            url {
                parameters.append("pageNum", pageNum)
            }
        }.body()
    }

    override suspend fun getPerfumer(perfumerId: Int): DataResponseDto<Any> {
        return httpClient.get("/perfumer/${perfumerId}").body()
    }
}