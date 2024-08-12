package com.hyangmoa.core_model.response

data class MagazineTastingCommentResponseDtoItem(
    val communityId: Int,
    val content: String,
    val nickname: String,
    val profileImg: String,
    val title: String
)