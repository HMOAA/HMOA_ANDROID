package com.hmoa.core_data.response

data class PerfumeDetailSecondResponseDto(
    val commentInfo:PerfumeCommentGetResponseDto,
    val similarPerfumes:List<PerfumeSimilarResponseDto>
)
