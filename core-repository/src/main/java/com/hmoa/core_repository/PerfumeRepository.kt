package com.hmoa.core_repository

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*

interface PerfumeRepository {
    fun getPerfumeTopDetail(perfumeId: Int): PerfumeDetailResponseDto
    fun getPerfumeBottomDetail(perfumeId: Int): PerfumeDetailSecondResponseDto
    fun postPerfumeMainPhoto(imageUrl: String): DataResponseDto<Any>
    fun postPerfumeAge(dto: AgeRequestDto, perfumeId: Int): PerfumeAgeResponseDto
    fun deletePerfumeAge(perfumeId: Int): PerfumeAgeResponseDto
    fun postPerfumeGender(dto: PerfumeGenderRequestDto, perfumeId: Int): PerfumeGenderResponseDto
    fun deletePerfumeGender(perfumeId: Int): PerfumeGenderResponseDto
    fun putPerfumeLike(perfumeId: Int): DataResponseDto<Any>
    fun deletePerfumeLike(perfumeId: Int): DataResponseDto<Any>
    fun postPerfumeWeather(dto: PerfumeWeatherRequestDto): PerfumeWeatherResponseDto
    fun deletePerfumeWeather(perfumeId: Int): PerfumeWeatherResponseDto
    fun getLikePerfumeList(): DataResponseDto<Any>

}