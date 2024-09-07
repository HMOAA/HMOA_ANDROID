package com.hmoa.core_domain.repository

import ResultResponse
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

interface PerfumeRepository {
    suspend fun getPerfumeTopDetail(perfumeId: String): ResultResponse<PerfumeDetailResponseDto>
    suspend fun getPerfumeBottomDetail(perfumeId: String): ResultResponse<PerfumeDetailSecondResponseDto>
    suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): ResultResponse<PerfumeAgeResponseDto>
    suspend fun deletePerfumeAge(perfumeId: String): PerfumeAgeResponseDto
    suspend fun postPerfumeGender(
        dto: PerfumeGenderRequestDto,
        perfumeId: String
    ): ResultResponse<PerfumeGenderResponseDto>

    suspend fun deletePerfumeGender(perfumeId: String): PerfumeGenderResponseDto
    suspend fun putPerfumeLike(perfumeId: String): DataResponseDto<Any>
    suspend fun deletePerfumeLike(perfumeId: String): DataResponseDto<Any>
    suspend fun postPerfumeWeather(
        dto: PerfumeWeatherRequestDto,
        perfumeId: String
    ): ResultResponse<PerfumeWeatherResponseDto>

    suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto
    suspend fun getLikePerfumes(): ResultResponse<DataResponseDto<List<PerfumeLikeResponseDto>>>
    suspend fun getRecentPerfumes() : ResultResponse<RecentPerfumeResponseDto>
}