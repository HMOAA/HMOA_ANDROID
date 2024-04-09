package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCommentAllResponseDto(
    val commentCount: Int,
    val comments: List<CommunityCommentWithLikedResponseDto>
)
