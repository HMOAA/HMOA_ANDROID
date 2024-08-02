package com.hmoa.core_network.service

import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.OrderResponseDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import com.hmoa.core_model.response.ProductListResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
interface HshopService {
    @GET("/shop/cart")
    suspend fun getCart(): ApiResponse<PostNoteSelectedResponseDto>
    @GET("/shop/note")
    suspend fun getNotes(): ApiResponse<ProductListResponseDto>
    @POST("/shop/note/order")
    suspend fun postNoteOrder(@Body dto: ProductListRequestDto): ApiResponse<OrderResponseDto>
    @POST("/shop/note/select")
    suspend fun postNotesSelected(@Body dto: ProductListRequestDto): ApiResponse<PostNoteSelectedResponseDto>
}

