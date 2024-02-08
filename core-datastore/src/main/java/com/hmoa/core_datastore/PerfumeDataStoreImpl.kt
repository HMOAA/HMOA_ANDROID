package com.hmoa.core_datastore

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.Perfume.PerfumeService

internal class PerfumeDataStoreImpl constructor(private val perfumeService: PerfumeService) : PerfumeDataStore {
    override suspend fun getPerfumeTopDetail(perfumeId: String): PerfumeDetailResponseDto {
        return perfumeService.getPerfumeTopDetail(perfumeId)
    }

    override suspend fun getPerfumeBottomDetail(perfumeId: String): PerfumeDetailSecondResponseDto {
        return perfumeService.getPerfumeBottomDetail(perfumeId)
    }

    override suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): PerfumeAgeResponseDto {
        return perfumeService.postPerfumeAge(dto, perfumeId)
    }

    override suspend fun deletePerfumeAge(perfumeId: String): PerfumeAgeResponseDto {
        return perfumeService.deletePerfumeAge(perfumeId)
    }

    override suspend fun postPerfumeGender(dto: PerfumeGenderRequestDto, perfumeId: String): PerfumeGenderResponseDto {
        return perfumeService.postPerfumeGender(dto, perfumeId)
    }

    override suspend fun deletePerfumeGender(perfumeId: String): PerfumeGenderResponseDto {
        return perfumeService.deletePerfumeGender(perfumeId)
    }

    override suspend fun putPerfumeLike(perfumeId: String): DataResponseDto<Any> {
        return perfumeService.putPerfumeLike(perfumeId)
    }

    override suspend fun deletePerfumeLike(perfumeId: String): DataResponseDto<Any> {
        return perfumeService.deletePerfumeLike(perfumeId)
    }

    override suspend fun postPerfumeWeather(
        perfumeId: String,
        dto: PerfumeWeatherRequestDto
    ): PerfumeWeatherResponseDto {
        return perfumeService.postPerfumeWeather(perfumeId, dto)
    }

    override suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto {
        return perfumeService.deletePerfumeWeather(perfumeId)
    }

    override suspend fun getLikePerfumes(): DataResponseDto<Any> {
        return perfumeService.getLikePerfumes()
    }

}