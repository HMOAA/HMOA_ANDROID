package com.hmoa.core_datastore.Hshop

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.OrderResponseDto
import com.hmoa.core_model.response.PostNoteSelectedResponseDto
import com.hmoa.core_model.response.ProductListResponseDto
import com.hmoa.core_network.service.HshopService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
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

    override suspend fun postNoteOrder(dto: ProductListRequestDto): ResultResponse<OrderResponseDto> {
        val result = ResultResponse<OrderResponseDto>()
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
}