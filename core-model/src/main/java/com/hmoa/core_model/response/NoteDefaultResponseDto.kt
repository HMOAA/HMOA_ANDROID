package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class NoteDefaultResponseDto(
    val noteId: Int,
    val noteSubtitle: String,
    val noteTitle: String
)
