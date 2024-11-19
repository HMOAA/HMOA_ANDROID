package com.hmoa.core_network.service

import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.*
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface HshopService {
    @GET("/shop/cart")
    suspend fun getCart(): ApiResponse<PostNoteSelectedResponseDto>

    @GET("/shop/note")
    suspend fun getNotes(): ApiResponse<ProductListResponseDto>

    @POST("/shop/note/order")
    suspend fun postNoteOrder(@Body dto: ProductListRequestDto): ApiResponse<PostNoteOrderResponseDto>

    @POST("/shop/note/select")
    suspend fun postNotesSelected(@Body dto: ProductListRequestDto): ApiResponse<PostNoteSelectedResponseDto>

    @GET("/shop/note/order/{orderId}")
    suspend fun getFinalOrderResult(
        @Path("orderId") orderId: Int
    ): ApiResponse<FinalOrderResponseDto>

    @DELETE("/shop/note/order/{orderId}/product/{productId}")
    suspend fun deleteNoteInOrder(
        @Path("orderId") orderId: Int,
        @Path("productId") productId: Int
    ): ApiResponse<FinalOrderResponseDto>

    @GET("/shop/order/me")
    suspend fun getMyOrders(): ApiResponse<List<GetMyOrderResponseDto>>

    @GET("/shop/order/description")
    suspend fun getOrderDescriptions(): ApiResponse<OrderDescriptionResponseDto>
}
