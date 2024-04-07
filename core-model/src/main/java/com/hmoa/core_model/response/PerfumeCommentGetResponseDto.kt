package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeCommentGetResponseDto(
    val commentCount: Int,
    val comments: Array<PerfumeCommentResponseDto>
)
