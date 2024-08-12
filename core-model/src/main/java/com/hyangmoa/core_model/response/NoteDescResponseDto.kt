package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class NoteDescResponseDto(
    val noteId : Int,
    val noteTitle : String,
    val noteSubtitle : String,
    val content : String
)
