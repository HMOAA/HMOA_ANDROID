package com.hyangmoa.core_network.service

import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.PerfumerDescResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface PerfumerService {
    @GET("/perfumer")
    suspend fun getPerfumers(@Field("pageNum") pageNum: String): DataResponseDto<Any>

    @GET("/perfumer/{perfumerId}")
    suspend fun getPerfumer(@Path("perfumerId") perfumerId: Int): ApiResponse<DataResponseDto<PerfumerDescResponseDto>>
}