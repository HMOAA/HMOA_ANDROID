package com.hyangmoa.core_datastore.Perfumer

import ResultResponse
import com.hyangmoa.core_model.data.ErrorMessage
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.PerfumerDescResponseDto
import com.hyangmoa.core_network.service.PerfumerService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
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
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
}