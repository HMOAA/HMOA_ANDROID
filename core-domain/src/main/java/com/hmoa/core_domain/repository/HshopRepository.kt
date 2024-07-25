package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.ProductListResponseDto

interface HshopRepository {
    suspend fun getNotesProduct(): ResultResponse<ProductListResponseDto>
    suspend fun postNotesSelected(notes: ProductListRequestDto): ResultResponse<Any>
}