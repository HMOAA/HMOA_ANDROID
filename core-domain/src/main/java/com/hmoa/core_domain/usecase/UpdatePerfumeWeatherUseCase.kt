package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.Weather
import com.hmoa.core_model.request.PerfumeWeatherRequestDto
import com.hmoa.core_model.response.PerfumeWeatherResponseDto
import javax.inject.Inject

class UpdatePerfumeWeatherUseCase @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) {
    suspend operator fun invoke(weather: Weather, perfumeId: Int): PerfumeWeatherResponseDto {
        val param = when (weather) {
            Weather.SPRING -> 1
            Weather.SUMMER -> 2
            Weather.AUTUMN -> 3
            Weather.WINTER -> 4
        }
        return perfumeRepository.postPerfumeWeather(PerfumeWeatherRequestDto(param), perfumeId.toString())
    }
}