package com.hmoa.core_network.service

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeAgeResponseDto
import com.hmoa.core_model.response.PerfumeDetailResponseDto
import com.hmoa.core_model.response.PerfumeDetailSecondResponseDto
import com.hmoa.core_model.response.PerfumeGenderResponseDto
import com.hmoa.core_model.response.PerfumeLikeResponseDto
import com.hmoa.core_model.response.PerfumeWeatherResponseDto
import com.hmoa.core_model.response.RecentPerfumeResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PerfumeService {
    @GET("/perfume/{perfumeId}")
    suspend fun getPerfumeTopDetail(@Path("perfumeId") perfumeId: String): ApiResponse<PerfumeDetailResponseDto>

    @POST("/perfume/{perfumeId}/2")
    suspend fun getPerfumeBottomDetail(@Path("perfumeId") perfumeId: String): ApiResponse<PerfumeDetailSecondResponseDto>

    @POST("/perfume/{perfumeId}/age")
    suspend fun postPerfumeAge(
        @Body dto: AgeRequestDto,
        @Path("perfumeId") perfumeId: String
    ): ApiResponse<PerfumeAgeResponseDto>

    @DELETE("/perfume/{perfumeId}/age")
    suspend fun deletePerfumeAge(@Path("perfumeId") perfumeId: String): PerfumeAgeResponseDto

    @POST("/perfume/{perfumeId}/gender")
    suspend fun postPerfumeGender(
        @Body dto: PerfumeGenderRequestDto,
        @Path("perfumeId") perfumeId: String
    ): ApiResponse<PerfumeGenderResponseDto>

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
    ): ApiResponse<PerfumeWeatherResponseDto>

    @DELETE("/perfume/{perfumeId}/weather")
    suspend fun deletePerfumeWeather(@Path("perfumeId") perfumeId: String): PerfumeWeatherResponseDto

    @GET("/perfume/like")
    suspend fun getLikePerfumes(): ApiResponse<DataResponseDto<List<PerfumeLikeResponseDto>>>

    @GET("/perfume/recentPerfume")
    suspend fun getRecentPerfumes() : ApiResponse<RecentPerfumeResponseDto>
}