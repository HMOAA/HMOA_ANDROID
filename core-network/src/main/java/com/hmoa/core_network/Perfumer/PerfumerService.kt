package com.hmoa.core_network.Perfumer

import com.hmoa.core_model.response.DataResponseDto

interface PerfumerService {
    suspend fun getPerfumers(pageNum: String): DataResponseDto<Any>
    suspend fun getPerfumer(perfumerId: Int): DataResponseDto<Any>
}