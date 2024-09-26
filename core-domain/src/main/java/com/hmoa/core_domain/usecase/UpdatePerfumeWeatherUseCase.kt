package com.hmoa.core_domain.usecase

import ResultResponse
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_domain.entity.data.Weather
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.PerfumeWeatherResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePerfumeWeatherUseCase @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) {
    suspend operator fun invoke(weather: Weather, perfumeId: Int): Flow<ResultResponse<PerfumeWeatherResponseDto>> {
        val param = when (weather) {
            Weather.SPRING -> 1
            Weather.SUMMER -> 2
            Weather.AUTUMN -> 3
            Weather.WINTER -> 4
        }
        return flow {
            val result = perfumeRepository.postPerfumeWeather(PerfumeWeatherRequestDto(param), perfumeId.toString())
            emit(result)
        }
    }
}