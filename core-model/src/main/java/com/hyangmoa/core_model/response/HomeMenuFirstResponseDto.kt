package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeMenuFirstResponseDto(
    val banner: String,
    val firstMenu: HomeMenuDefaultResponseDto,
    val mainImage: String
)
