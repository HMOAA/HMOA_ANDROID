package com.hmoa.core_model.response

import com.hmoa.core_model.request.NoteResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class RecommendNotesResponseDto(
    val recommendNotes: List<NoteResponseDto>
)
