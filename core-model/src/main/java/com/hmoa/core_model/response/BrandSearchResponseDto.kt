package com.hmoa.core_model.response

data class BrandSearchResponseDto(
    val brandList: List<BrandDefaultResponseDto>,
    val consonant: Int
)
