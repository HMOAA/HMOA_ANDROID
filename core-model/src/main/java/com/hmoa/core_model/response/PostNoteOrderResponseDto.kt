package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PostNoteOrderResponseDto(
    val isExistMemberAddress: Boolean,
    val isExistMemberInfo: Boolean,
    val orderId: Int,
    val orderStatus: String
)