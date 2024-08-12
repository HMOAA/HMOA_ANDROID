package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeWeatherResponseDto(
    val autumn: Int,
    val selected: Int,
    val spring: Int,
    val summer: Int,
    val winter: Int,
    val writed: Boolean
)
