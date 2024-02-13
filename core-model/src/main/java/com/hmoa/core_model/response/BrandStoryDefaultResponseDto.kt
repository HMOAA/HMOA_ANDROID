package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class BrandStoryDefaultResponseDto(
    val brandStoryId: Int,
    val brandStorySubtitle: String,
    val brandStoryTitle: String
)
