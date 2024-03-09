package com.hmoa.core_network.service

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*
import retrofit2.http.*

interface PerfumeService {
    @GET("/perfume/{perfumeId}")
    suspend fun getPerfumeTopDetail(@Path("perfumeId") perfumeId: String): PerfumeDetailResponseDto

    @GET("/perfume/{perfumeId}/2")
    suspend fun getPerfumeBottomDetail(@Path("perfumeId") perfumeId: String): PerfumeDetailSecondResponseDto

    @FormUrlEncoded
    @POST("/perfume/{perfumeId}/age")
    suspend fun postPerfumeAge(@Body dto: AgeRequestDto, @Path("perfumeId") perfumeId: String): PerfumeAgeResponseDto

    @DELETE("/perfume/{perfumeId}/age")
    suspend fun deletePerfumeAge(@Path("perfumeId") perfumeId: String): PerfumeAgeResponseDto

    @FormUrlEncoded
    @POST("/perfume/{perfumeId}/gender")
    suspend fun postPerfumeGender(
        @Body dto: PerfumeGenderRequestDto,
        @Path("perfumeId") perfumeId: String
    ): PerfumeGenderResponseDto

    @DELETE("/perfume/{perfumeId}/gender")
    suspend fun deletePerfumeGender(@Path("perfumeId") perfumeId: String): PerfumeGenderResponseDto

    @PUT("/perfume/{perfumeId}/like")
    suspend fun putPerfumeLike(@Path("perfumeId") perfumeId: String): DataResponseDto<Any>

    @DELETE("/perfume/{perfumeId}/like")
    suspend fun deletePerfumeLike(@Path("perfumeId") perfumeId: String): DataResponseDto<Any>

    @POST("/perfume/{perfumeId}/weather")
    suspend fun postPerfumeWeather(
        @Path("perfumeId") perfumeId: String,
        @Body dto: PerfumeWeatherRequestDto
    ): PerfumeWeatherResponseDto

    @DELETE("/perfume/{perfumeId}/weather")
    suspend fun deletePerfumeWeather(@Path("perfumeId") perfumeId: String): PerfumeWeatherResponseDto

    @GET("/perfume/like")
    suspend fun getLikePerfumes(): DataResponseDto<Any>
}