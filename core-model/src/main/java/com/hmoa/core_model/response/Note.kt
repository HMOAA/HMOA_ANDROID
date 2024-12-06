package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val noteContent: String,
    val noteName: String
)