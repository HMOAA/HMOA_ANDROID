package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeSurveyResponseDto(
    val priceQuestion: SurveyQuestionResponseDto,
    val noteQuestion: NoteQuestionDto
)
