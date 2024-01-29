package com.hmoa.core_model.response

data class HomeMenuDefaultResponseDto(
    val perfumeList: List<HomeMenuPerfumeResponseDto>,
    val title: String
)
