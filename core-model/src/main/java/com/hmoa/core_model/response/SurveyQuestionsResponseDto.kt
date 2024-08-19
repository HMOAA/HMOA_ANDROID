package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyQuestionsResponseDto(
    val questions: List<SurveyQuestionResponseDto>,
    val title: String
)