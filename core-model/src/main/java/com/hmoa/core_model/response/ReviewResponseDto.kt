package com.hmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponseDto(
    val author: String,
    val content: String,
    val createdAt: String,
    val hbtiPhotos: List<Photo>,
    val hbtiReviewId: Int,
    val heartCount: Int,
    val imagesCount: Int,
    val isLiked: Boolean,
    val isWrited: Boolean,
    val orderTitle: String,
    val profileImgUrl: String,
)
