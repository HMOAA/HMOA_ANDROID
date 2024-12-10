package com.hmoa.core_datastore.Hshop

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.HshopService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class HshopRemoteDataStoreImpl @Inject constructor(
    private val hshopService: HshopService,
    private val authenticator: Authenticator
) : HshopRemoteDataStore {
    override suspend fun getCart(): ResultResponse<PostNoteSelectedResponseDto> {
        val result = ResultResponse<PostNoteSelectedResponseDto>()
        hshopService.getCart().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
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
        hshopService.postNoteOrder(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
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
        hshopService.getFinalOrderResult(orderId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun deleteNoteInOrder(
        orderId: Int,
        productId: Int
    ): ResultResponse<FinalOrderResponseDto> {
        val result = ResultResponse<FinalOrderResponseDto>()
        hshopService.deleteNoteInOrder(orderId, productId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun getMyOrders(): ResultResponse<List<GetMyOrderResponseDto>> {
        val result = ResultResponse<List<GetMyOrderResponseDto>>()
        hshopService.getMyOrders().suspendOnError {
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }.suspendOnSuccess {
            result.data = this.data
        }
        return result
    }

    override suspend fun getOrderDescriptions(): ResultResponse<OrderDescriptionResponseDto> {
        val result = ResultResponse<OrderDescriptionResponseDto>()
        hshopService.getOrderDescriptions().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    hshopService.getOrderDescriptions().suspendOnSuccess { result.data = this.data }
                })
        }
        return result
    }
}
