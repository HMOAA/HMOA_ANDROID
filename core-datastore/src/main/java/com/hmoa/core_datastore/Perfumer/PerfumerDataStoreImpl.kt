package com.hmoa.core_datastore.Perfumer

import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.Perfumer.PerfumerService

internal class PerfumerDataStoreImpl constructor(private val perfumerService: PerfumerService) : PerfumerDataStore {
    override suspend fun getPerfumers(pageNum: String): DataResponseDto<Any> {
        return perfumerService.getPerfumers(pageNum)
    }

    override suspend fun getPerfumer(perfumerId: Int): DataResponseDto<Any> {
        return perfumerService.getPerfumer(perfumerId)
    }
}