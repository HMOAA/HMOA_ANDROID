package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.OrderResponseDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import com.hmoa.core_model.response.ProductListResponseDto

interface HshopRepository {
    suspend fun getCart(): ResultResponse<PostNoteSelectedResponseDto>
    suspend fun getNotesProduct(): ResultResponse<ProductListResponseDto>
    suspend fun postNoteOrder(dto: ProductListRequestDto): ResultResponse<OrderResponseDto>
    suspend fun postNotesSelected(dto: ProductListRequestDto): ResultResponse<PostNoteSelectedResponseDto>
}