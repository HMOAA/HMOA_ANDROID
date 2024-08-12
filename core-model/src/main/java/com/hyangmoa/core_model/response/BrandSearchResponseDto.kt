package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class BrandSearchResponseDto(
    val brandList: List<BrandDefaultResponseDto>,
    var consonant: Int
)