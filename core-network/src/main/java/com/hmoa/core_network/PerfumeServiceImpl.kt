package com.hmoa.core_network

import com.hmoa.core_model.response.PerfumeDetailResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class PerfumeServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : PerfumeService {

    override suspend fun getPerfumeTopDetail(perfumeId: String): PerfumeDetailResponseDto {
        val response = httpClient.get("/perfume") {
            url {
                parameters.append("perfumeId", perfumeId.toString())
            }
        }
        return response.body()
    }
}