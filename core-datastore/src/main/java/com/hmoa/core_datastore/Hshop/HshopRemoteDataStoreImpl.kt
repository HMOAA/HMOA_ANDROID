package com.hmoa.core_datastore.Hshop

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.FinalOrderResponseDto
import com.hmoa.core_model.response.GetMyOrderResponseDto
import com.hmoa.core_model.response.PostNoteOrderResponseDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import com.hmoa.core_model.response.ProductListResponseDto
import com.hmoa.core_network.service.HshopService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class HshopRemoteDataStoreImpl @Inject constructor(private val hshopService: HshopService) : HshopRemoteDataStore {
    override suspend fun getCart(): ResultResponse<PostNoteSelectedResponseDto> {
        val result = ResultResponse<PostNoteSelectedResponseDto>()
        hshopService.getCart().suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }
    override suspend fun getNotes(): ResultResponse<ProductListResponseDto> {
        var result = ResultResponse<ProductListResponseDto>()
        hshopService.getNotes().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun postNoteOrder(dto: ProductListRequestDto): ResultResponse<PostNoteOrderResponseDto> {
        val result = ResultResponse<PostNoteOrderResponseDto>()
        hshopService.postNoteOrder(dto).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun postNotesSelected(dto: ProductListRequestDto): ResultResponse<PostNoteSelectedResponseDto> {
        val result = ResultResponse<PostNoteSelectedResponseDto>()
        hshopService.postNotesSelected(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
    override suspend fun getFinalOrderResult(orderId: Int): ResultResponse<FinalOrderResponseDto> {
        val result = ResultResponse<FinalOrderResponseDto>()
        hshopService.getFinalOrderResult(orderId).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun deleteNoteInOrder(
        orderId: Int,
        productId: Int
    ): ResultResponse<FinalOrderResponseDto> {
        val result = ResultResponse<FinalOrderResponseDto>()
        hshopService.deleteNoteInOrder(orderId, productId).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }
    override suspend fun getMyOrders(): ResultResponse<List<GetMyOrderResponseDto>> {
        val result = ResultResponse<List<GetMyOrderResponseDto>>()
        hshopService.getMyOrders().suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}