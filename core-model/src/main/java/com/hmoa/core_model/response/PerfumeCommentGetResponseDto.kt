package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeCommentGetResponseDto(
    val commentCount: Int,
    var comments: List<PerfumeCommentResponseDto>,
    val lastPage: Boolean
)
