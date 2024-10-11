package com.hmoa.core_model.request

import kotlinx.serialization.Serializable

@Serializable
data class NoteResponseDto(
    val content: String,
    val noteId: Int,
    val noteName: String,
    val notePhotoUrl: String
)