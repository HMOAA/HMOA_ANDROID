package com.hmoa.core_model.response

data class MemberLoginResponseDto(
    val authToken: String,
    val existedMember: Boolean,
    val rememberedToken: String
)
