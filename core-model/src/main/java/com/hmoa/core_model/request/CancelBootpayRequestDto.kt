package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class CancelBootpayRequestDto(
    val cancelReason: String,
    val receiptId: String
)
