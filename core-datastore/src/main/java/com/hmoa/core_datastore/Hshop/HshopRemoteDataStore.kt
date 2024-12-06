package com.hmoa.core_datastore.Hshop

import ResultResponse
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.*

interface HshopRemoteDataStore {
    suspend fun getCart(): ResultResponse<PostNoteSelectedResponseDto>
    suspend fun getNotes(): ResultResponse<ProductListResponseDto>
    suspend fun postNoteOrder(dto: ProductListRequestDto): ResultResponse<PostNoteOrderResponseDto>
    suspend fun postNotesSelected(dto: ProductListRequestDto): ResultResponse<PostNoteSelectedResponseDto>
    suspend fun getFinalOrderResult(orderId: Int): ResultResponse<FinalOrderResponseDto>
    suspend fun deleteNoteInOrder(orderId: Int, productId: Int): ResultResponse<FinalOrderResponseDto>
    suspend fun getMyOrders(): ResultResponse<List<GetMyOrderResponseDto>>
    suspend fun getOrderDescriptions(): ResultResponse<OrderDescriptionResponseDto>
}
