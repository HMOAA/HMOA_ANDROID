package com.hmoa.core_network.service

import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.ProductListResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface HshopService {
    @GET("/shop/note")
    suspend fun getNotes(): ApiResponse<ProductListResponseDto>

    @FormUrlEncoded
    @POST("/shop/note/select")
    suspend fun postNotesSelected(dto: ProductListRequestDto): ApiResponse<Any>
}