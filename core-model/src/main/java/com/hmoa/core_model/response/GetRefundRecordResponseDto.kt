package com.hmoa.core_model.response

import com.hmoa.core_model.data.OrderStatus
import kotlinx.serialization.Serializable

@Serializable
data class GetRefundRecordResponseDto(
    val orderId: Int,
    val orderStatus: OrderStatus,
    val orderProducts: FinalOrderResponseDto,
    val createdAt: String,
    val courierCompany: String?,
    val trackingNumber: String?
)
