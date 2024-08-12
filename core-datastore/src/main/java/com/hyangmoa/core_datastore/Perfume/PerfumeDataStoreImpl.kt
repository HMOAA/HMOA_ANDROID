package com.hyangmoa.core_datastore.Perfume

import ResultResponse
import com.hyangmoa.core_model.data.ErrorMessage
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
import com.hyangmoa.core_network.service.PerfumeService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject


class PerfumeDataStoreImpl @Inject constructor(private val perfumeService: PerfumeService) : PerfumeDataStore {
    override suspend fun getPerfumeTopDetail(perfumeId: String): ResultResponse<PerfumeDetailResponseDto> {
        var result = ResultResponse<PerfumeDetailResponseDto>()
        perfumeService.getPerfumeTopDetail(perfumeId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun getPerfumeBottomDetail(perfumeId: String): ResultResponse<PerfumeDetailSecondResponseDto> {
        var result = ResultResponse<PerfumeDetailSecondResponseDto>()
        perfumeService.getPerfumeBottomDetail(perfumeId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): ResultResponse<PerfumeAgeResponseDto> {
        var result = ResultResponse<PerfumeAgeResponseDto>()
        perfumeService.postPerfumeAge(dto, perfumeId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun deletePerfumeAge(perfumeId: String): PerfumeAgeResponseDto {
        return perfumeService.deletePerfumeAge(perfumeId)
    }

    override suspend fun postPerfumeGender(
        dto: PerfumeGenderRequestDto,
        perfumeId: String
    ): ResultResponse<PerfumeGenderResponseDto> {
        var result = ResultResponse<PerfumeGenderResponseDto>()
        perfumeService.postPerfumeGender(dto, perfumeId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
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
    ): ResultResponse<PerfumeWeatherResponseDto> {
        var result = ResultResponse<PerfumeWeatherResponseDto>()
        perfumeService.postPerfumeWeather(perfumeId, dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun deletePerfumeWeather(perfumeId: String): PerfumeWeatherResponseDto {
        return perfumeService.deletePerfumeWeather(perfumeId)
    }

    override suspend fun getLikePerfumes(): ResultResponse<DataResponseDto<List<PerfumeLikeResponseDto>>> {
        val result = ResultResponse<DataResponseDto<List<PerfumeLikeResponseDto>>>()
        perfumeService.getLikePerfumes().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
    override suspend fun getRecentPerfumes(): ResultResponse<RecentPerfumeResponseDto> {
        val result = ResultResponse<RecentPerfumeResponseDto>()
        perfumeService.getRecentPerfumes().suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }
}