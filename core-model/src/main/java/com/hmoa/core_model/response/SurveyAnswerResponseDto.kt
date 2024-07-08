package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyAnswerResponseDto(
    val option: String,
    val optionId: Int
)
