package com.hmoa.core_model.response

import com.hmoa.core_model.data.OrderStatus
import kotlinx.serialization.Serializable

@Serializable
data class OrderRecordDto(
    val courierCompany: String,
    val orderId: Int,
    val orderProducts: FinalOrderResponseDto,
    val orderStatus: OrderStatus,
    val trackingNumber: String
)