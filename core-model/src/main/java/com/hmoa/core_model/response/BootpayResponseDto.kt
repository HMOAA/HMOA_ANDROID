package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class BootpayResponseDto(
    val event: String,
    val data: BootpayOrderResultData,
    val bootpay_event: Boolean
)