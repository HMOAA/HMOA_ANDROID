package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class TermDescResponseDto(
    val termId: Int,
    val termTitle: String,
    val termEnglishTitle: String,
    val content: String
)