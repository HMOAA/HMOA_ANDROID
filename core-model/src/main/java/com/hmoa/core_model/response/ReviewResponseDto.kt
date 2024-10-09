package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponseDto(
    val hbtiReviewId: Int,
    val profileImgUrl: String,
    val author: String,
    val content: String,
    val imagesCount: Int,
    val hbtiPhotos: List<Photo>,
    val createdAt: String,
    val isWrited: Boolean,
    val isLiked: Boolean,
    val orderTitle: String
)
