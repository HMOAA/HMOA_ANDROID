package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeAgeResponseDto(
    var age: Float,
    var writed: Boolean
)
