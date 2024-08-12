package com.hyangmoa.core_model.response

data class MagazineSummaryResponseDto(
    val magazineId: Int,
    var preview: String,
    val previewImgUrl: String,
    val title: String
)