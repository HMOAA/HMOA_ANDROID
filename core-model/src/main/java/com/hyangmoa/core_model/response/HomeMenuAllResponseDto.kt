package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeMenuAllResponseDto(
    val brandName: String,
    val imgUrl: String,
    val liked: Boolean,
    val perfumeId: Int,
    val perfumeName: String
)
