package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseDto(
    var authToken: String,
    var rememberedToken: String
)
