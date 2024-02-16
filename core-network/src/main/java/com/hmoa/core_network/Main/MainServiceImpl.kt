package com.hmoa.core_network.Main

import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class MainServiceImpl @Inject constructor(
    private val httpClient : HttpClient
): MainService {

    override suspend fun getFirst(): HomeMenuFirstResponseDto {
        return httpClient.get("/main/first").body()
    }

    override suspend fun getFirstMenu(): HomeMenuAllResponseDto {
        return httpClient.get("/main/firstMenu").body()
    }

    override suspend fun getSecond(): HomeMenuDefaultResponseDto {
        return httpClient.get("/main/second").body()
    }

    override suspend fun getSecondMenu(): List<HomeMenuAllResponseDto> {
        return httpClient.get("/main/secondMenu").body()
    }

    override suspend fun getThirdMenu(): List<HomeMenuAllResponseDto> {
        return httpClient.get("/main/thirdMenu").body()
    }
}