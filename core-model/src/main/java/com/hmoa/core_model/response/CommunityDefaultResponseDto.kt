package com.hmoa.core_model.response

import com.hmoa.core_data.Category

data class CommunityDefaultResponseDto(
    val author: String,
    var category: String,
    val communityPhotos: CommunityPhotoDefaultResponseDto,
    val content: String,
    val id: Int,
    val imagesCount: Int,
    val myProfileImgUrl: String,
    val profileImgUrl: String,
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
