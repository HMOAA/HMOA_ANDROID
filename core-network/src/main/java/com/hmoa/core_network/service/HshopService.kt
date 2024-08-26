package com.hmoa.core_network.service

import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.FinalOrderResponseDto
import com.hmoa.core_model.response.PostNoteOrderResponseDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import com.hmoa.core_model.response.ProductListResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
}

