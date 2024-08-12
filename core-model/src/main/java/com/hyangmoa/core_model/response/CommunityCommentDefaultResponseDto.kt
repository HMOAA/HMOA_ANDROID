package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCommentDefaultResponseDto(
    val content: String,
    val createAt: String,
    val heartCount: Int,
    val id: Int,
    val liked: Boolean,
    val nickname: String,
    val parentId: Int,
    val profileImg: String,
    val writed: Boolean
)