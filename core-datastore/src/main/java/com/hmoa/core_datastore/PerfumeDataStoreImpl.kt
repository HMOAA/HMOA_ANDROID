package com.hmoa.core_datastore

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.Perfume.PerfumeService

private class PerfumeDataStoreImpl constructor(private val perfumeService: PerfumeService) : PerfumeDataStore {
    override suspend fun getPerfumeTopDetail(perfumeId: String): PerfumeDetailResponseDto {
        return perfumeService.getPerfumeTopDetail(perfumeId)
    }

    override suspend fun getPerfumeBottomDetail(perfumeId: String): PerfumeDetailSecondResponseDto {
        perfumeService.getPerfumeBottomDetail(perfumeId)
    }

    override suspend fun postPerfumeMainPhoto(imageUrl: String): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): PerfumeAgeResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun deletePerfumeAge(perfumeId: String): PerfumeAgeResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun postPerfumeGender(dto: PerfumeGenderRequestDto, perfumeId: String): PerfumeGenderResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun deletePerfumeGender(perfumeId: String): PerfumeGenderResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun putPerfumeLike(perfumeId: String): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePerfumeLike(perfumeId: String): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun postPerfumeWeather(dto: PerfumeWeatherRequestDto): PerfumeWeatherResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun getLikePerfumeList(): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

}