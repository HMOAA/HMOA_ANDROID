package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyOptionResponseDto(
    val option: String,
    val optionId: Int
)
