package com.hmoa.core_datastore.Perfumer

import ResultResponse
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumerDescResponseDto

interface PerfumerDataStore {
    suspend fun getPerfumers(pageNum: String): DataResponseDto<Any>
    suspend fun getPerfumer(perfumerId: Int): ResultResponse<DataResponseDto<PerfumerDescResponseDto>>
}