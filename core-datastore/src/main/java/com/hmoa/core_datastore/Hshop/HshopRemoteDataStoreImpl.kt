package com.hmoa.core_datastore.Hshop

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.ProductListResponseDto
import com.hmoa.core_network.service.HshopService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class HshopRemoteDataStoreImpl @Inject constructor(private val hshopService: HshopService) : HshopRemoteDataStore {
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

    override suspend fun postNotesSelected(dto: ProductListRequestDto): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        hshopService.postNotesSelected(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
}