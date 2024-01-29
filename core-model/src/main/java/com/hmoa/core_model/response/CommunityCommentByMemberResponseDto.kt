package com.hmoa.core_model.response

data class CommunityCommentByMemberResponseDto(
    val author: String,
    val commentId: Int,
    val communityId: Int,
    val content: String,
    val profileImg: String,
    val time: String,
    val writed: Boolean
)
