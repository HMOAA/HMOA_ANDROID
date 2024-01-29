package com.hmoa.core_model.response

data class TokenResponseDto(
    val authToken: String,
    val rememberedToken: String
)
