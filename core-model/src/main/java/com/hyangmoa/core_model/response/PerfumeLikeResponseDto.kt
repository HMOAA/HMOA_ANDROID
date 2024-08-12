package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeLikeResponseDto(
    val perfumeId: Int,
    val brandName: String,
    val koreanName: String,
    val englishName: String,
    val perfumeImageUrl: String,
    val price: Int
)