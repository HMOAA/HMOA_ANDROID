package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeAgeResponseDto(
    val age: Int,
    val writed: Boolean
)
