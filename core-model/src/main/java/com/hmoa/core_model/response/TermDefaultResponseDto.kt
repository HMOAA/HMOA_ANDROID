package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class TermDefaultResponseDto(
    val termEnglishTitle: String,
    val termId: Int,
    val termTitle: String
)
