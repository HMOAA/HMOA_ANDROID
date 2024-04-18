package com.hmoa.core_datastore.Perfumer

import ResultResponse
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumerDescResponseDto
import com.hmoa.core_network.service.PerfumerService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class PerfumerDataStoreImpl @Inject constructor(private val perfumerService: PerfumerService) :
    PerfumerDataStore {
    override suspend fun getPerfumers(pageNum: String): DataResponseDto<Any> {
        return perfumerService.getPerfumers(pageNum)
    }

    override suspend fun getPerfumer(perfumerId: Int): ResultResponse<DataResponseDto<PerfumerDescResponseDto>> {
        val result = ResultResponse<DataResponseDto<PerfumerDescResponseDto>>()
        perfumerService.getPerfumer(perfumerId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }
}