package com.hyangmoa.core_repository

import ResultResponse
import com.hyangmoa.core_datastore.Perfume.PerfumeDataStore
import com.hyangmoa.core_domain.repository.PerfumeRepository
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
import javax.inject.Inject

class PerfumeRepositoryImpl @Inject constructor(private val perfumeDataStore: PerfumeDataStore) : PerfumeRepository {
    override suspend fun getPerfumeTopDetail(perfumeId: String): ResultResponse<PerfumeDetailResponseDto> {
        return perfumeDataStore.getPerfumeTopDetail(perfumeId)
    }

    override suspend fun getPerfumeBottomDetail(perfumeId: String): ResultResponse<PerfumeDetailSecondResponseDto> {
        return perfumeDataStore.getPerfumeBottomDetail(perfumeId)
    }

    override suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): ResultResponse<PerfumeAgeResponseDto> {
        return perfumeDataStore.postPerfumeAge(dto, perfumeId)
    }

    override suspend fun deletePerfumeAge(perfumeId: String): PerfumeAgeResponseDto {
        return perfumeDataStore.deletePerfumeAge(perfumeId)
    }

    override suspend fun postPerfumeGender(
        dto: PerfumeGenderRequestDto,
        perfumeId: String
    ): ResultResponse<PerfumeGenderResponseDto> {
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
    ): ResultResponse<PerfumeWeatherResponseDto> {
        return perfumeDataStore.postPerfumeWeather(perfumeId, dto)
    }

    override suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto {
        return perfumeDataStore.deletePerfumeWeather(perfumeId)
    }

    override suspend fun getLikePerfumes(): ResultResponse<DataResponseDto<List<PerfumeLikeResponseDto>>> {
        return perfumeDataStore.getLikePerfumes()
    }

    override suspend fun getRecentPerfumes(): ResultResponse<RecentPerfumeResponseDto> {
        return perfumeDataStore.getRecentPerfumes()
    }
}