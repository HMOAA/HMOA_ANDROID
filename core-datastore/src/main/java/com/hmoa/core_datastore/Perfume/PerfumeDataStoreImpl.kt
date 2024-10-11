package com.hmoa.core_datastore.Perfume

import ResultResponse
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.PerfumeService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject


class PerfumeDataStoreImpl @Inject constructor(
    private val perfumeService: PerfumeService,
    private val authenticator: Authenticator
) : PerfumeDataStore {
    override suspend fun getPerfumeTopDetail(perfumeId: String): ResultResponse<PerfumeDetailResponseDto> {
        var result = ResultResponse<PerfumeDetailResponseDto>()
        perfumeService.getPerfumeTopDetail(perfumeId)
            .suspendOnSuccess {
                result.data = this.data
            }
            .suspendOnError {
                authenticator.handleApiError(
                    rawMessage = this.message(),
                    handleErrorMesssage = { result.errorMessage = it },
                    onCompleteTokenRefresh = {
                        perfumeService.getPerfumeTopDetail(perfumeId).suspendOnSuccess { result.data = this.data }
                    }
                )
            }
        return result
    }


    override suspend fun getPerfumeBottomDetail(perfumeId: String): ResultResponse<PerfumeDetailSecondResponseDto> {
        var result = ResultResponse<PerfumeDetailSecondResponseDto>()
        perfumeService.getPerfumeBottomDetail(perfumeId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeService.getPerfumeBottomDetail(perfumeId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun postPerfumeAge(dto: AgeRequestDto, perfumeId: String): ResultResponse<PerfumeAgeResponseDto> {
        var result = ResultResponse<PerfumeAgeResponseDto>()
        perfumeService.postPerfumeAge(dto, perfumeId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeService.postPerfumeAge(dto, perfumeId).suspendOnSuccess { result.data = this.data }
                }
            )
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
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeService.postPerfumeGender(dto, perfumeId).suspendOnSuccess { result.data = this.data }
                }
            )
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
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeService.postPerfumeWeather(perfumeId, dto).suspendOnSuccess { result.data = this.data }
                }
            )
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
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeService.getLikePerfumes().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getRecentPerfumes(): ResultResponse<RecentPerfumeResponseDto> {
        val result = ResultResponse<RecentPerfumeResponseDto>()
        perfumeService.getRecentPerfumes().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumeService.getRecentPerfumes().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}