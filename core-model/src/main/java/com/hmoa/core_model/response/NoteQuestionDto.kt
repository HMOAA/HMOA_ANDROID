package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class NoteQuestionDto(
    val content: String,
    val answer: List<NotesAndCategoryDto>
)
