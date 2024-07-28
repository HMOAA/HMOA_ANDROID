package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class NoteProduct(
    val notes: List<Note>,
    val notesCount: Int,
    val price: Int,
    val productId: Int,
    val productName: String,
    val productPhotoUrl: String
)