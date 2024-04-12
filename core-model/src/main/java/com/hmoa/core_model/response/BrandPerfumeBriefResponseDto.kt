package com.hmoa.core_model.response

data class BrandPerfumeBriefResponseDto(
    val brandId: Int?,
    val brandName: String?,
    val brandEnglishName: String?,
    val brandImgUrl: String?,
    val perfumeId: Int,
    val perfumeImgUrl: String?,
    val perfumeName: String?,
    val heartCount: Int?,
    val liked: Boolean?
)