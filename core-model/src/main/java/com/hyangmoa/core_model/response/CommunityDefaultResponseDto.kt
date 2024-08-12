package com.hyangmoa.core_model.response

import com.hyangmoa.core_model.Category
import kotlinx.serialization.Serializable

@Serializable
data class CommunityDefaultResponseDto(
    val author: String,
    var category: String,
    val communityPhotos: List<CommunityPhotoDefaultResponseDto>,
    val content: String,
    val heartCount : Int,
    val id: Int,
    val imagesCount: Int,
    val liked : Boolean,
    val myProfileImgUrl: String?,
    val profileImgUrl: String?,
    val time: String,
    val title: String,
    val writed: Boolean
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
