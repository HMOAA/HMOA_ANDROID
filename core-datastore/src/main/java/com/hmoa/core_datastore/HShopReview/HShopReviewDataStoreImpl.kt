package com.hmoa.core_datastore.HShopReview

import ResultResponse
import com.hmoa.core_datastore.Mapper.transformRequestBody
import com.hmoa.core_datastore.Mapper.transformToMultipartBody
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.response.GetMyOrderResponseDto
import com.hmoa.core_model.response.PagingData
import com.hmoa.core_model.response.ReviewResponseDto
import com.hmoa.core_network.service.HShopReviewService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class HShopReviewDataStoreImpl @Inject constructor(
    private val hShopReviewService: HShopReviewService
) : HShopReviewDataStore{
    override suspend fun getReviews(page: Int): ResultResponse<PagingData<ReviewResponseDto>> {
        val result = ResultResponse<PagingData<ReviewResponseDto>>()
        hShopReviewService.getReviews(page).suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun postReview(
        image: Array<File>,
        orderId: Int,
        content: String
    ): ResultResponse<ReviewResponseDto> {
        val result = ResultResponse<ReviewResponseDto>()
        hShopReviewService.postReview(
            image.transformToMultipartBody(),
            orderId.transformRequestBody(),
            content.transformRequestBody()
        ).suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun putReviewLike(reviewId: Int): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        hShopReviewService.putReviewLike(reviewId).suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun deleteReviewLike(reviewId: Int): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        hShopReviewService.deleteReviewLike(reviewId).suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    override suspend fun getMyOrders(): ResultResponse<List<GetMyOrderResponseDto>> {
        val result = ResultResponse<List<GetMyOrderResponseDto>>()
        hShopReviewService.getMyOrders().suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}