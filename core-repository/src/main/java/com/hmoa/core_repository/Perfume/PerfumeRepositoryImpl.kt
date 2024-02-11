package com.hmoa.core_repository.Perfume

import com.hmoa.core_datastore.Perfume.PerfumeDataStore
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*

internal class PerfumeRepositoryImpl(private val perfumeDataStore: PerfumeDataStore) : PerfumeRepository {
    override suspend fun getPerfumeTopDetail(perfumeId: String): PerfumeDetailResponseDto {
        return perfumeDataStore.getPerfumeTopDetail(perfumeId)
    }

    override suspend fun getPerfumeBottomDetail(perfumeId: String): PerfumeDetailSecondResponseDto {
        return perfumeDataStore.getPerfumeBottomDetail(perfumeId)
    }

    override suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): PerfumeAgeResponseDto {
        return perfumeDataStore.postPerfumeAge(dto, perfumeId)
    }

    override suspend fun deletePerfumeAge(perfumeId: String): PerfumeAgeResponseDto {
        return perfumeDataStore.deletePerfumeAge(perfumeId)
    }

    override suspend fun postPerfumeGender(dto: PerfumeGenderRequestDto, perfumeId: String): PerfumeGenderResponseDto {
        return perfumeDataStore.postPerfumeGender(dto, perfumeId)
    }

    override suspend fun deletePerfumeGender(perfumeId: String): PerfumeGenderResponseDto {
        return perfumeDataStore.deletePerfumeGender(perfumeId)
    }

    override suspend fun putPerfumeLike(perfumeId: String): DataResponseDto<Any> {
        return perfumeDataStore.putPerfumeLike(perfumeId)
    }

    override suspend fun deletePerfumeLike(perfumeId: String): DataResponseDto<Any> {
        return perfumeDataStore.deletePerfumeLike(perfumeId)
    }

    override suspend fun postPerfumeWeather(
        dto: PerfumeWeatherRequestDto,
        perfumeId: String
    ): PerfumeWeatherResponseDto {
        return perfumeDataStore.postPerfumeWeather(perfumeId, dto)
    }

    override suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto {
        return perfumeDataStore.deletePerfumeWeather(perfumeId)
    }

    override suspend fun getLikePerfumes(): DataResponseDto<Any> {
        return perfumeDataStore.getLikePerfumes()
    }
}