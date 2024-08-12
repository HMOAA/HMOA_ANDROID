package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class BrandDefaultResponseDto(
    
    val brandId: Int,
    val brandImageUrl: String,
    val brandName: String,
    val englishName: String
)
