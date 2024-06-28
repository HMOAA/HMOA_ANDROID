package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class AlarmResponse(
    val content: String,
    val createdAt: String,
    val deeplink: String,
    val id: Int,
    var read: Boolean,
    val title: String
)