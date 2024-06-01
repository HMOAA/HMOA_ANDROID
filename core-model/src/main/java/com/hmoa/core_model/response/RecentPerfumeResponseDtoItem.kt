package com.hmoa.core_model.response

data class RecentPerfumeResponseDtoItem(
    val brandName: String,
    val perfumeId: Int,
    val perfumeImgUrl: String,
    val perfumeName: String,
    val relaseDate: String
)