package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PostNoteSelectedResponseDto(
    val noteProducts: List<NoteProduct>,
    val totalPrice: Int
)