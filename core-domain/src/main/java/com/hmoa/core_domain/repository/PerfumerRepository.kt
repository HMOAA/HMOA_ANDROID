package com.hmoa.core_domain.repository

import com.hmoa.core_model.response.DataResponseDto

interface PerfumerRepository {
    suspend fun getPerfumers(pageNum: String): DataResponseDto<Any>
    suspend fun getPerfumer(perfumerId: Int): DataResponseDto<Any>
}