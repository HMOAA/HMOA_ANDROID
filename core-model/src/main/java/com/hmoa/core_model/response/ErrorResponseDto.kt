package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val code: String,
    val message: String
)
