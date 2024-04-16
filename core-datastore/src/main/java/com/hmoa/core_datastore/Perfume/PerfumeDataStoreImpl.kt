package com.hmoa.core_datastore.Perfume

import ResultResponse
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.service.PerfumeService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class PerfumeDataStoreImpl @Inject constructor(private val perfumeService: PerfumeService) : PerfumeDataStore {
    override suspend fun getPerfumeTopDetail(perfumeId: String): ResultResponse<PerfumeDetailResponseDto> {
        var result = ResultResponse<PerfumeDetailResponseDto>()
        perfumeService.getPerfumeTopDetail(perfumeId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getPerfumeBottomDetail(perfumeId: String): ResultResponse<PerfumeDetailSecondResponseDto> {
        var result = ResultResponse<PerfumeDetailSecondResponseDto>()
        perfumeService.getPerfumeBottomDetail(perfumeId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
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