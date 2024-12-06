package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class ContentAndTitle(
    val content: String,
    val title: String
)
