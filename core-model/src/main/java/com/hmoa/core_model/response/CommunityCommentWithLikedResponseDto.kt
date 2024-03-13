package com.hmoa.core_model.response

data class CommunityCommentWithLikedResponseDto(
    val author : String,
    val commentId : Int,
    val content : String,
    val heartCount : Int,
    val liked : Boolean,
    val profileImg : String,
    val time : String,
    val writed : Boolean
)
