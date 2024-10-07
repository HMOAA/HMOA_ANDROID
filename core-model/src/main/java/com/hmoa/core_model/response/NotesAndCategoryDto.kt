package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class NotesAndCategoryDto(
    val category: String,
    val notes: List<String>
)
