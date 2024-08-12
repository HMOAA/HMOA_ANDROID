package com.hyangmoa.core_datastore.Perfume

import ResultResponse
import com.hyangmoa.core_model.request.AgeRequestDto
import com.hyangmoa.core_model.request.PerfumeGenderRequestDto
import com.hyangmoa.core_model.request.PerfumeWeatherRequestDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.PerfumeAgeResponseDto
import com.hyangmoa.core_model.response.PerfumeDetailResponseDto
import com.hyangmoa.core_model.response.PerfumeDetailSecondResponseDto
import com.hyangmoa.core_model.response.PerfumeGenderResponseDto
import com.hyangmoa.core_model.response.PerfumeLikeResponseDto
import com.hyangmoa.core_model.response.PerfumeWeatherResponseDto
import com.hyangmoa.core_model.response.RecentPerfumeResponseDto

interface PerfumeDataStore {
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
        perfumeId: String,
        dto: PerfumeWeatherRequestDto
    ): ResultResponse<PerfumeWeatherResponseDto>

    suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto
    suspend fun getLikePerfumes(): ResultResponse<DataResponseDto<List<PerfumeLikeResponseDto>>>
    suspend fun getRecentPerfumes() : ResultResponse<RecentPerfumeResponseDto>
}