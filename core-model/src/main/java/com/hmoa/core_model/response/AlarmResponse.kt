package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class AlarmResponse(
    val id: Int,
    val title: String,
    val content: String,
    val deeplink: String,
    val senderProfileImg : String?,
    val createdAt: String,
    var read: Boolean
)