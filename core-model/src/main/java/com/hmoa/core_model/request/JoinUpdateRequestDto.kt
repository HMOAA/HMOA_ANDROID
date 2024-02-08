package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class JoinUpdateRequestDto(
    val age: Int,
    val nickname: String,
    val sex: Boolean
)
