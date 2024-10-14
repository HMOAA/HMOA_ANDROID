package com.hmoa.core_datastore.HShopReview

import ResultResponse
import com.hmoa.core_model.response.GetMyOrderResponseDto
import com.hmoa.core_model.response.PagingData
import com.hmoa.core_model.response.ReviewResponseDto
import java.io.File

interface HShopReviewDataStore {
    suspend fun getReviews(page: Int): ResultResponse<PagingData<ReviewResponseDto>>
    suspend fun postReview(
        image: Array<File>,
        orderId: Int,
        content: String,
    ): ResultResponse<ReviewResponseDto>
    suspend fun putReviewLike(reviewId: Int): ResultResponse<Any>
    suspend fun deleteReviewLike(reviewId: Int): ResultResponse<Any>
    suspend fun getMyOrders(): ResultResponse<List<GetMyOrderResponseDto>>
}