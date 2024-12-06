package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeSurveyAnswerRequestDto(
    val maxPrice: Int,
    val minPrice: Int,
    val notes: List<String>
)