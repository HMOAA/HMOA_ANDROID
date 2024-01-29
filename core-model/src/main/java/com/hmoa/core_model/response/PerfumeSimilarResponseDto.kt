package com.hmoa.core_model.response

data class PerfumeSimilarResponseDto(
    val brandId: Int,
    val brandName: String,
    val perfumeId: Int,
    val perfumeImgUrl: String,
    val perfumeName: String
)
