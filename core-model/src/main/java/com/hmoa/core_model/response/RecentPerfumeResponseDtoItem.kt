package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class RecentPerfumeResponseDtoItem(
    val brandName: String,
    val perfumeId: Int,
    val perfumeImgUrl: String,
    val perfumeName: String,
    val releaseDate: String?
)
