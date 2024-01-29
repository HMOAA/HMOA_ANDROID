package com.hmoa.core_model.response

data class HomeMenuAllResponseDto(
    val brandName: String,
    val imgUrl: String,
    val liked: Boolean,
    val perfumeId: Int,
    val perfumeName: String
)
