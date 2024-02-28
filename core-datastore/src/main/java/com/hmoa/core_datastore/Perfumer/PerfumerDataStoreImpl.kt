package com.hmoa.core_datastore.Perfumer

import com.hmoa.core_model.response.DataResponseDto
import corenetwork.Perfumer.PerfumerService
import javax.inject.Inject

class PerfumerDataStoreImpl @Inject constructor(private val perfumerService: PerfumerService) :
    PerfumerDataStore {
    override suspend fun getPerfumers(pageNum: String): DataResponseDto<Any> {
        return perfumerService.getPerfumers(pageNum)
    }

    override suspend fun getPerfumer(perfumerId: Int): DataResponseDto<Any> {
        return perfumerService.getPerfumer(perfumerId)
    }
}