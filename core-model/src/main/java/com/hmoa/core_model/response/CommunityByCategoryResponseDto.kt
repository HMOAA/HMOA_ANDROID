package com.hmoa.core_model.response

import com.hmoa.core_data.Category

data class CommunityByCategoryResponseDto(
    var category: String,
    val communityId: Int,
    val title: String
) {
    init {
        category = when (category) {
            Category.시향기.name -> Category.시향기.name
            Category.자유.name -> Category.자유.name
            Category.추천.name -> Category.추천.name
            else -> throw IllegalArgumentException("Invalide category: $category")
        }
    }
}
