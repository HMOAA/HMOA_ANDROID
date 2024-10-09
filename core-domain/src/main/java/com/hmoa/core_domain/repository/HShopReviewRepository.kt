package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.response.PagingData
import com.hmoa.core_model.response.ReviewResponseDto
import java.io.File

interface HShopReviewRepository {
    suspend fun getReviews(page: Int): ResultResponse<PagingData<ReviewResponseDto>>
    suspend fun postReview(
        image: Array<File>,
        orderId: Int,
        content: String,
    ): ResultResponse<ReviewResponseDto>
    suspend fun putReviewLike(reviewId: Int): ResultResponse<Any>
    suspend fun deleteReviewLike(reviewId: Int): ResultResponse<Any>
}