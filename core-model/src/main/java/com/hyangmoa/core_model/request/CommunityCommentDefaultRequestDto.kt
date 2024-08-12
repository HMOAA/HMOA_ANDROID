package com.hyangmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCommentDefaultRequestDto(
    val content: String
)
