package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCommentDefaultResponseDto(
    val content : String,
    val createAt : String,
    val heartCount : Int,
    val id : Int,
    val liked : Boolean,
    val nickname : String,
    val perfumeId : Int,
    val profileImg : String,
    val writed : Boolean
)
