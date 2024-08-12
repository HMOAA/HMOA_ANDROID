package com.hyangmoa.core_datastore.Perfumer

import ResultResponse
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.PerfumerDescResponseDto

interface PerfumerDataStore {
    suspend fun getPerfumers(pageNum: String): DataResponseDto<Any>
    suspend fun getPerfumer(perfumerId: Int): ResultResponse<DataResponseDto<PerfumerDescResponseDto>>
}