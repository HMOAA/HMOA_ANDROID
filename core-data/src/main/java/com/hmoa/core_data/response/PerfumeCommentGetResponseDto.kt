package com.hmoa.core_data.response

data class PerfumeCommentGetResponseDto(
    val commentCount:Int,
    val comments:List<PerfumeCommentResponseDto>
)
