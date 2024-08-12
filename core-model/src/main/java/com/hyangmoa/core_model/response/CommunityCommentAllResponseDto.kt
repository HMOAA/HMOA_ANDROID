package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCommentAllResponseDto(
    val commentCount: Int,
    val comments: List<CommunityCommentWithLikedResponseDto>,
    val lastPage : Boolean
)
