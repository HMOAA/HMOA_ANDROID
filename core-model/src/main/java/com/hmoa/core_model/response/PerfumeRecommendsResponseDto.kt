package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeRecommendsResponseDto(
    val recommendPerfumes: List<PerfumeRecommendResponseDto>
)
