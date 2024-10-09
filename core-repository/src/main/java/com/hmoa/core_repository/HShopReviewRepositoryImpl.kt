package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.HShopReview.HShopReviewDataStore
import com.hmoa.core_domain.repository.HShopReviewRepository
import com.hmoa.core_model.response.PagingData
import com.hmoa.core_model.response.ReviewResponseDto
import java.io.File
import javax.inject.Inject

class HShopReviewRepositoryImpl @Inject constructor(
    private val hShopReviewDataStore: HShopReviewDataStore
): HShopReviewRepository {
    override suspend fun getReviews(page: Int): ResultResponse<PagingData<ReviewResponseDto>> {
        return hShopReviewDataStore.getReviews(page)
    }

    override suspend fun postReview(
        image: Array<File>,
        orderId: Int,
        content: String
    ): ResultResponse<ReviewResponseDto> {
        return hShopReviewDataStore.postReview(image, orderId, content)
    }

    override suspend fun putReviewLike(reviewId: Int): ResultResponse<Any> {
        return hShopReviewDataStore.putReviewLike(reviewId)
    }

    override suspend fun deleteReviewLike(reviewId: Int): ResultResponse<Any> {
        return hShopReviewDataStore.deleteReviewLike(reviewId)
    }
}