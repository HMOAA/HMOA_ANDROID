package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class ExceptionResponseDto(
    val code: String,
    val message: String
)
