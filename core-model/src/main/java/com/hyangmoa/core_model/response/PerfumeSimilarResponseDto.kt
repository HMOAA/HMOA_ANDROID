package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeSimilarResponseDto(
    val brandId: Int,
    val brandName: String,
    val perfumeId: Int,
    val perfumeImgUrl: String,
    val perfumeName: String
)
