package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class FinalOrderResponseDto(
    val paymentAmount: Int,
    val productInfo: PostNoteSelectedResponseDto,
    val shippingAmount: Int,
    val totalAmount: Int
)
