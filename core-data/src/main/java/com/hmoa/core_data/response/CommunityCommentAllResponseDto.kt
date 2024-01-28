package com.hmoa.core_data.response

data class CommunityCommentAllResponseDto(
    val commentCount:Int,
    val comments:List<CommunityCommentDefaultResponseDto>
)
