package com.hmoa.core_data.response

data class BrandSearchResponseDto(
    val brandList: List<BrandDefaultResponseDto>,
    val consonant: Int
)
