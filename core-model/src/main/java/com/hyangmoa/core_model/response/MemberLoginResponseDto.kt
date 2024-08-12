package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class MemberLoginResponseDto(
    var authToken: String,
    var existedMember: Boolean,
    var rememberedToken: String
)
