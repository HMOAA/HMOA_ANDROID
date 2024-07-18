package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class SurveyRespondRequestDto(
    val optionIds: List<Int>
)