package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeGenderRequestDto(
    val gender: Int
)
