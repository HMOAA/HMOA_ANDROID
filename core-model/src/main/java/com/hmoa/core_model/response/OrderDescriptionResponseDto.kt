package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderDescriptionResponseDto(
    val orderDescriptionImgUrl: String,
    val orderDescriptions: List<ContentAndTitle>
)
