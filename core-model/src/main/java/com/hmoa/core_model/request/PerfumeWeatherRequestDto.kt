package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeWeatherRequestDto(
    val weather: Int
)
