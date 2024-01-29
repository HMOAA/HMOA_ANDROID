package com.hmoa.core_model.response

data class PerfumeSearchResponseDto(
    val brandName: String,
    val heart: Boolean,
    val perfumeId: Int,
    val perfumeImageUrl: String,
    val perfumeName: String
)
