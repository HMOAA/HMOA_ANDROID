package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeDetailSecondResponseDto(
    val commentInfo: PerfumeCommentGetResponseDto,
    val similarPerfumes: Array<PerfumeSimilarResponseDto>
)
