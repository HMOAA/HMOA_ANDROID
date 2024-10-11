package com.hmoa.core_datastore.Perfumer

import ResultResponse
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumerDescResponseDto
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.PerfumerService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class PerfumerDataStoreImpl @Inject constructor(
    private val perfumerService: PerfumerService,
    private val authenticator: Authenticator
) :
    PerfumerDataStore {
    override suspend fun getPerfumers(pageNum: String): DataResponseDto<Any> {
        return perfumerService.getPerfumers(pageNum)
    }

    override suspend fun getPerfumer(perfumerId: Int): ResultResponse<DataResponseDto<PerfumerDescResponseDto>> {
        val result = ResultResponse<DataResponseDto<PerfumerDescResponseDto>>()
        perfumerService.getPerfumer(perfumerId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    perfumerService.getPerfumer(perfumerId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}