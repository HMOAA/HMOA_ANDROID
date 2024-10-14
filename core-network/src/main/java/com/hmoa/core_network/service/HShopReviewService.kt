package com.hmoa.core_network.service

import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.GetMyOrderResponseDto
import com.hmoa.core_model.response.PagingData
import com.hmoa.core_model.response.ReviewResponseDto
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface HShopReviewService {
    @GET("/shop/review")
    suspend fun getReviews(@Query("page") page: Int)
        : ApiResponse<PagingData<ReviewResponseDto>>
    @Multipart
    @POST("/shop/review")
    suspend fun postReview(
        @Part images: Array<MultipartBody.Part>,
        @Part orderId: RequestBody,
        @Part content: RequestBody
    ): ApiResponse<ReviewResponseDto>
    @Multipart
    @POST("/shop/review/{reviewId}")
    suspend fun postEditReview(
        @Part images: Array<MultipartBody.Part>,
        @Part deleteReviewPhotoIds: Array<RequestBody>,
        @Part content: RequestBody,
        @Path("reviewId") reviewId: Int
    ): ApiResponse<ReviewResponseDto>
    @PUT("/shop/review/{reviewId}/like")
    suspend fun putReviewLike(@Path("reviewId") reviewId: Int)
        : ApiResponse<DataResponseDto<Any>>
    @DELETE("/shop/review/{reviewId}/like")
    suspend fun deleteReviewLike(@Path("reviewId") reviewId: Int)
        : ApiResponse<DataResponseDto<Any>>
    @GET("/shop/order/me")
    suspend fun getMyOrders(): ApiResponse<List<GetMyOrderResponseDto>>
}