package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PostNoteOrderResponseDto(
    val existMemberAddress: Boolean,
    val existMemberInfo: Boolean,
    val orderId: Int,
    val orderStatus: String
)