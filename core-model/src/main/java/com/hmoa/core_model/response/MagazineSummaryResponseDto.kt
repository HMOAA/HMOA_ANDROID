package com.hmoa.core_model.response

data class MagazineSummaryResponseDto(
    val magazineId: Int,
    val preview: String,
    val previewImgUrl: String,
    val title: String
)