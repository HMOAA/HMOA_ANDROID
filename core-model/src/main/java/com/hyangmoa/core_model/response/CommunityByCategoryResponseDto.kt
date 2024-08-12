package com.hyangmoa.core_model.response

import com.hyangmoa.core_model.Category
import kotlinx.serialization.Serializable

@Serializable
data class CommunityByCategoryResponseDto(
    var category: String,
    val commentCount : Int,
    val communityId: Int,
    val heartCount : Int,
    val liked : Boolean,
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
