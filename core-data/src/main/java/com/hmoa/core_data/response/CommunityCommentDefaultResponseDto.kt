package com.hmoa.core_data.response

data class CommunityCommentDefaultResponseDto(
    val author:String,
    val commentId:Int,
    val content:String,
    val profileImg:String,
    val time:String,
    val writed:Boolean
)
