package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PerfumeCommentResponseDto(
    var content: String,
    val createdAt: String?,
    var heartCount: Int,
    val id: Int,
    var liked: Boolean,
    val nickname: String,
    val perfumeId: Int,
    val profileImg: String?,
    val writed: Boolean
)
