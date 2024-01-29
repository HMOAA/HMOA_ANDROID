package com.hmoa.core_model.response

data class PerfumeCommentGetResponseDto(
    val commentCount: Int,
    val comments: List<PerfumeCommentResponseDto>
)
