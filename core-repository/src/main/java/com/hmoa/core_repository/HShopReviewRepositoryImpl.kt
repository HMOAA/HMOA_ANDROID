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

    override suspend fun getReview(reviewId: Int): ResultResponse<ReviewResponseDto> {
        return hShopReviewDataStore.getReview(reviewId)
    }
    override suspend fun postEditReview(
        image: Array<File>,
        deleteReviewPhotoIds: Array<Int>,
        content: String,
        reviewId: Int
    ): ResultResponse<ReviewResponseDto> {
        return hShopReviewDataStore.postEditReview(image, deleteReviewPhotoIds, content, reviewId)
    }

    override suspend fun deleteReview(reviewId: Int): ResultResponse<Any> {
        return hShopReviewDataStore.deleteReview(reviewId)
    }

    override suspend fun putReviewLike(reviewId: Int): ResultResponse<Any> {
        return hShopReviewDataStore.putReviewLike(reviewId)
    }

    override suspend fun deleteReviewLike(reviewId: Int): ResultResponse<Any> {
        return hShopReviewDataStore.deleteReviewLike(reviewId)
    }

    override suspend fun getMyReviews(cursor: Int): ResultResponse<PagingData<ReviewResponseDto>> {
        return hShopReviewDataStore.getMyReviews(cursor)
    }
}