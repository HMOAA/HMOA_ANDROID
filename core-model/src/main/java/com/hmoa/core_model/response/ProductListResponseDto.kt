package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponseDto(
    val data: List<ProductResponseDto>
)