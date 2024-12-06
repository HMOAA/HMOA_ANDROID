package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class BrandDefaultResponseDto(
    val brandId: Int,
    val brandName: String,
    val englishName: String
)
