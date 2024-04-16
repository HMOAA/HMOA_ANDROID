package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCommentWithLikedResponseDto(
    val commentId : Int,
    val content : String,
    val author : String,
    val profileImg : String,
    val time : String,
    val writed : Boolean,
    val liked : Boolean,
    val heartCount : Int
)
