package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeRecommendResponseDto(
    val brandname: String,
    val perfumeEnglishName: String,
    val perfumeId: Int,
    val perfumeImageUrl: String,
    val perfumeName: String,
    val price: Int
)
