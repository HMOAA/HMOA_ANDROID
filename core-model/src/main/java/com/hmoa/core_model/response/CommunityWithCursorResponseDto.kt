package com.hmoa.core_model.response

data class CommunityWithCursorResponseDto(
    val communities : List<CommunityByCategoryResponseDto>,
    val lastPage : Boolean
)
