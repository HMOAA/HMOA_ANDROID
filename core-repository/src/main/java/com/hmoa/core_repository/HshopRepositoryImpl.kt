package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Hshop.HshopRemoteDataStore
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.FinalOrderResponseDto
import com.hmoa.core_model.response.GetMyOrderResponseDto
import com.hmoa.core_model.response.PostNoteOrderResponseDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import com.hmoa.core_model.response.ProductListResponseDto
import javax.inject.Inject

class HshopRepositoryImpl @Inject constructor(private val hshopRemoteDataStore: HshopRemoteDataStore) :
    HshopRepository {
    override suspend fun getCart(): ResultResponse<PostNoteSelectedResponseDto> {
        return hshopRemoteDataStore.getCart()
    }
    override suspend fun getNotesProduct(): ResultResponse<ProductListResponseDto> {
        return hshopRemoteDataStore.getNotes()
    }

    override suspend fun postNoteOrder(dto: ProductListRequestDto): ResultResponse<PostNoteOrderResponseDto> {
        return hshopRemoteDataStore.postNoteOrder(dto)
    }
    override suspend fun postNotesSelected(dto: ProductListRequestDto): ResultResponse<PostNoteSelectedResponseDto> {
        return hshopRemoteDataStore.postNotesSelected(dto)
    }
    override suspend fun getFinalOrderResult(orderId: Int): ResultResponse<FinalOrderResponseDto> {
        return hshopRemoteDataStore.getFinalOrderResult(orderId)
    }

    override suspend fun deleteNoteInOrder(
        orderId: Int,
        productId: Int
    ): ResultResponse<FinalOrderResponseDto> {
        return hshopRemoteDataStore.deleteNoteInOrder(orderId, productId)
    }

    override suspend fun getMyOrders(): ResultResponse<List<GetMyOrderResponseDto>> {
        return hshopRemoteDataStore.getMyOrders()
    }
}