package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeMenuPerfumeResponseDto(
    val brandName: String,
    val imgUrl: String,
    val perfumeId: Int,
    val perfumeName: String
)
