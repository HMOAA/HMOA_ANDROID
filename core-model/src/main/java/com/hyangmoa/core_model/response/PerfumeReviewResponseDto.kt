package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeReviewResponseDto(
    val age: PerfumeAgeResponseDto,
    val gender: PerfumeGenderResponseDto,
    val weather: PerfumeWeatherResponseDto
)
