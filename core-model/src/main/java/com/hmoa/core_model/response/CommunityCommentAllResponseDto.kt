package com.hmoa.core_model.response

data class CommunityCommentAllResponseDto(
    val commentCount: Int,
    val comments: List<CommunityCommentDefaultResponseDto>
)
