package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeCommentRequestDto(
    val content: String
)
