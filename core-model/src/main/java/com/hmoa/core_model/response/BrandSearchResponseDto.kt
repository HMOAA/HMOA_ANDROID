package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class BrandSearchResponseDto(
    val brandList: List<BrandDefaultResponseDto>,
    val consonant: Int
)
