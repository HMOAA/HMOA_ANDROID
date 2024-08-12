package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class CommunityWithCursorResponseDto(
    val communites : List<CommunityByCategoryResponseDto>,
    val lastPage : Boolean
)
