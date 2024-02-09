package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCommentReportRequestDto(
    val targetId: Int
)
