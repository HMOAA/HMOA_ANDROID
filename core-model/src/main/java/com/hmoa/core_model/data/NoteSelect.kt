package com.hmoa.core_model.data

data class NoteSelect(
    val productId: Int,
    val isSelected: Boolean,
    val nodeFaceIndex: Int?,
    val isRecommended: Boolean,
)