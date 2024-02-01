package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseDto(
    val authToken: String,
    val rememberedToken: String
)
