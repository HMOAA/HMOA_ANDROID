package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyQuestionResponseDto(
    val answers: List<SurveyOptionResponseDto>,
    val content: String,
    val isMultipleChoice: Boolean,
    val questionId: Int
)
