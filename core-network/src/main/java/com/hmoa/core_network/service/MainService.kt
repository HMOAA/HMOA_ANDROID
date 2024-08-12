package com.hmoa.core_network.service

import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface MainService {
    @GET("/main/first")
    suspend fun getFirst(): ApiResponse<HomeMenuFirstResponseDto>

    @GET("/main/firstMenu")
    suspend fun getFirstMenu(): ApiResponse<List<HomeMenuAllResponseDto>>

    @GET("/main/second")
    suspend fun getSecond(): ApiResponse<List<HomeMenuDefaultResponseDto>>

    @GET("/main/secondMenu")
    suspend fun getSecondMenu(): ApiResponse<List<HomeMenuAllResponseDto>>

    @GET("/main/thirdMenu")
    suspend fun getThirdMenu(): ApiResponse<List<HomeMenuAllResponseDto>>
}