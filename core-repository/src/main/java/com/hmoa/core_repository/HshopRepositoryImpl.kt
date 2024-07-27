package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Hshop.HshopRemoteDataStore
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.ProductListResponseDto
import javax.inject.Inject

class HshopRepositoryImpl @Inject constructor(private val hshopRemoteDataStore: HshopRemoteDataStore) :
    HshopRepository {
    override suspend fun getNotesProduct(): ResultResponse<ProductListResponseDto> {
        return hshopRemoteDataStore.getNotes()
    }

    override suspend fun postNotesSelected(notes: ProductListRequestDto): ResultResponse<Any> {
        return hshopRemoteDataStore.postNotesSelected(notes)
    }
}