package com.hmoa.core_datastore.Hshop

import ResultResponse
import com.hmoa.core_model.request.ProductListRequestDto
import com.hmoa.core_model.response.ProductListResponseDto
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.HshopService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class HshopRemoteDataStoreImpl @Inject constructor(
    private val hshopService: HshopService,
    private val authenticator: Authenticator
) : HshopRemoteDataStore {
    override suspend fun getNotes(): ResultResponse<ProductListResponseDto> {
        var result = ResultResponse<ProductListResponseDto>()
        hshopService.getNotes().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    hshopService.getNotes().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun postNotesSelected(dto: ProductListRequestDto): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        hshopService.postNotesSelected(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    hshopService.postNotesSelected(dto).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}