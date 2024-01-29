package com.hmoa.core_model.response

data class PerfumeDetailSecondResponseDto(
    val commentInfo: PerfumeCommentGetResponseDto,
    val similarPerfumes: List<PerfumeSimilarResponseDto>
)
