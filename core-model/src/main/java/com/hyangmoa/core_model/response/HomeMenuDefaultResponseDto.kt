package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class HomeMenuDefaultResponseDto(
    val perfumeList: List<HomeMenuPerfumeResponseDto>,
    val title: String
)
