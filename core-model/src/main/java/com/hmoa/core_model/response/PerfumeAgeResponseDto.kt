package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeAgeResponseDto(
    var age: Int,
    var writed: Boolean
)
