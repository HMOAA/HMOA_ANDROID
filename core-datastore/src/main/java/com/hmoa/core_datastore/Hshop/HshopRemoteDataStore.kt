package com.hmoa.core_datastore.Hshop

import ResultResponse
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.ProductListResponseDto

interface HshopRemoteDataStore {
    suspend fun getNotes(): ResultResponse<ProductListResponseDto>
    suspend fun postNotesSelected(dto: ProductListRequestDto): ResultResponse<Any>
}