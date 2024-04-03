package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeDetailResponseDto(
    val baseNote: String,
    val brandEnglishName: String,
    val brandId: Int,
    val brandImgUrl: String,
    val brandName: String,
    val englishName: String,
    val heartNote: String,
    val heartNum: Int,
    val koreanName: String,
    val liked: Boolean,
    val notePhotos: Array<String>,
    val perfumeId: Int,
    val perfumeImageUrl: String,
    val price: Int,
    val priceVolume: Int,
    val review: PerfumeReviewResponseDto?,
    val singleNote: Array<String>?,
    val sortType: Int,
    val topNote: String,
    val volume: Array<Int>,
)
