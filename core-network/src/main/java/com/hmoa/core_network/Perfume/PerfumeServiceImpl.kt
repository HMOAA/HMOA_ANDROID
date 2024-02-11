package com.hmoa.core_network.Perfume

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import javax.inject.Inject

internal class PerfumeServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : PerfumeService {

    override suspend fun getPerfumeTopDetail(perfumeId: String): PerfumeDetailResponseDto {
        return httpClient.get("/perfume/${perfumeId}").body()
    }

    override suspend fun getPerfumeBottomDetail(perfumeId: String): PerfumeDetailSecondResponseDto {
        return httpClient.get("/perfume/${perfumeId}/2").body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): PerfumeAgeResponseDto {
        val response = httpClient.post("/perfume/${perfumeId}/age") {
            url {
                body = dto
            }
        }
        return response.body()
    }

    override suspend fun deletePerfumeAge(perfumeId: String): PerfumeAgeResponseDto {
        return httpClient.delete("/perfume/${perfumeId}/age").body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postPerfumeGender(dto: PerfumeGenderRequestDto, perfumeId: String): PerfumeGenderResponseDto {
        val response = httpClient.post("/perfume/${perfumeId}/gender") {
            url {
                body = dto
            }
        }
        return response.body()
    }

    override suspend fun deletePerfumeGender(perfumeId: String): PerfumeGenderResponseDto {
        return httpClient.delete("/perfume/${perfumeId}/gender").body()
    }

    override suspend fun putPerfumeLike(perfumeId: String): DataResponseDto<Any> {
        return httpClient.put("/perfume/${perfumeId}/like").body()
    }

    override suspend fun deletePerfumeLike(perfumeId: String): DataResponseDto<Any> {
        return httpClient.delete("/perfume/${perfumeId}/like").body()
    }

    @OptIn(InternalAPI::class)
    override suspend fun postPerfumeWeather(
        perfumeId: String,
        dto: PerfumeWeatherRequestDto
    ): PerfumeWeatherResponseDto {
        return httpClient.post("/perfume/${perfumeId}/weather") {
            body = dto
        }.body()
    }

    override suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto {
        return httpClient.delete("/perfume/${perfumeId}/weather").body()
    }

    override suspend fun getLikePerfumes(): DataResponseDto<Any> {
        return httpClient.get("/perfume/like").body()
    }
}