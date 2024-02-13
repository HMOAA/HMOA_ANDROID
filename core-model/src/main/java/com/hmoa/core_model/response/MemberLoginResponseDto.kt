package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class MemberLoginResponseDto(
    val authToken: String,
    val existedMember: Boolean,
    val rememberedToken: String
)
