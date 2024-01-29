package com.hmoa.core_model.response

data class PerfumeReviewResponseDto(
    val age: PerfumeAgeResponseDto,
    val gender: PerfumeGenderResponseDto,
    val weather: PerfumeWeatherResponseDto
)
