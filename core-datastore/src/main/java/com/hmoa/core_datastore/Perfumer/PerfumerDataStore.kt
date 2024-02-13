package com.hmoa.core_datastore.Perfumer

import com.hmoa.core_model.response.DataResponseDto

interface PerfumerDataStore {
    suspend fun getPerfumers(pageNum: String): DataResponseDto<Any>
    suspend fun getPerfumer(perfumerId: Int): DataResponseDto<Any>
}