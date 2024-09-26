package com.hmoa.core_domain.entity.data

data class NoteSelect(
    val productId: Int,
    val isSelected: Boolean,
    val nodeFaceIndex: Int?,
    val isRecommended: Boolean,
)