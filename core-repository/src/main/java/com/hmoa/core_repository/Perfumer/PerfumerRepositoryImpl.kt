package com.hmoa.core_repository.Perfumer

import com.hmoa.core_datastore.Perfumer.PerfumerDataStore
import com.hmoa.core_model.response.DataResponseDto

internal class PerfumerRepositoryImpl constructor(private val perfumerDataStore: PerfumerDataStore) :
    PerfumerRepository {
    override suspend fun getPerfumers(pageNum: String): DataResponseDto<Any> {
        return perfumerDataStore.getPerfumers(pageNum)
    }

    override suspend fun getPerfumer(perfumerId: Int): DataResponseDto<Any> {
        return perfumerDataStore.getPerfumer(perfumerId)
    }
}