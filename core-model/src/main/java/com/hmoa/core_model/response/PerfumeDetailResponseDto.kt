package com.hmoa.core_model.response

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
    val notePhotos: List<Int>,
    val perfumeId: Int,
    val perfumeImageUrl: String,
    val price: Int,
    val priceVolume: Int,
    val review: PerfumeReviewResponseDto,
    val singleNote: List<String>,
    val sortType: Int,
    val topNote: String,
    val volume: List<Int>
)
