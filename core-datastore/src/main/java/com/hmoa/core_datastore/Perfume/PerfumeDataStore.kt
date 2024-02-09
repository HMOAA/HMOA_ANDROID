package com.hmoa.core_datastore.Perfume

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*

interface PerfumeDataStore {
    suspend fun getPerfumeTopDetail(perfumeId: String): PerfumeDetailResponseDto
    suspend fun getPerfumeBottomDetail(perfumeId: String): PerfumeDetailSecondResponseDto
    suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): PerfumeAgeResponseDto
    suspend fun deletePerfumeAge(perfumeId: String): PerfumeAgeResponseDto
    suspend fun postPerfumeGender(dto: PerfumeGenderRequestDto, perfumeId: String): PerfumeGenderResponseDto
    suspend fun deletePerfumeGender(perfumeId: String): PerfumeGenderResponseDto
    suspend fun putPerfumeLike(perfumeId: String): DataResponseDto<Any>
    suspend fun deletePerfumeLike(perfumeId: String): DataResponseDto<Any>
    suspend fun postPerfumeWeather(perfumeId: String, dto: PerfumeWeatherRequestDto): PerfumeWeatherResponseDto
    suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto
    suspend fun getLikePerfumes(): DataResponseDto<Any>
}