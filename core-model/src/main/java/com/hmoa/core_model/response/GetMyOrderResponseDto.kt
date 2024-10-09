package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetMyOrderResponseDto(
    val orderId: Int,
    val orderInfo: String
)
