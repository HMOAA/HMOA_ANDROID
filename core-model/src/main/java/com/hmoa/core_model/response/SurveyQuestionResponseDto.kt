package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyQuestionResponseDto(
    val answers: List<SurveyAnswerResponseDto>,
    val content: String,
    val questionId: Int
)
