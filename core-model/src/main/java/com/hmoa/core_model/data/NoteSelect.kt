package com.hmoa.core_model.data

data class NoteSelect(
    val productId: Int,
    val isSelected: Boolean,
    val selectedIndex: Int?,
    val isRecommended: Boolean,
)