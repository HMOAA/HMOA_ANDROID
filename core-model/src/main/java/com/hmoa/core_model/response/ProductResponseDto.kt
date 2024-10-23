package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    val productId: Int,
    val productName: String,
    val productDetails: String,
    val productPhotoUrl: String,
    val isRecommended: Boolean,
    val price: Int,
)