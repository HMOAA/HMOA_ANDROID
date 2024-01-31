package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeSearchResponseDto(
    val brandName: String,
    val heart: Boolean,
    val perfumeId: Int,
    val perfumeImageUrl: String,
    val perfumeName: String
)
