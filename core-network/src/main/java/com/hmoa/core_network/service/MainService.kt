package com.hmoa.core_network.service

import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto
import retrofit2.http.GET

interface MainService {
    @GET("/main/first")
    suspend fun getFirst(): HomeMenuFirstResponseDto

    @GET("/main/firstMenu")
    suspend fun getFirstMenu(): HomeMenuAllResponseDto

    @GET("/main/second")
    suspend fun getSecond(): HomeMenuDefaultResponseDto

    @GET("/main/secondMenu")
    suspend fun getSecondMenu(): List<HomeMenuAllResponseDto>

    @GET("/main/thirdMenu")
    suspend fun getThirdMenu(): List<HomeMenuAllResponseDto>
}