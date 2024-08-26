package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseDto(
    val memberAddress: MemberAddressDto,
    val memberInfo: MemberSimpleInfoDto,
    val orderId: Int,
    val orderStatus: String,
    val paymentAmount: Int,
    val productInfo: PostNoteSelectedResponseDto,
    val shippingAmount: Int,
    val totalAmount: Int
)
