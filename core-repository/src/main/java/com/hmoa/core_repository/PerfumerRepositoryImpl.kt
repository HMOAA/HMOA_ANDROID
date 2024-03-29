package com.hmoa.core_repository

import com.hmoa.core_datastore.Perfumer.PerfumerDataStore
import com.hmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class PerfumerRepositoryImpl @Inject constructor(private val perfumerDataStore: PerfumerDataStore) :
    com.hmoa.core_domain.repository.PerfumerRepository {
    override suspend fun getPerfumers(pageNum: String): DataResponseDto<Any> {
        return perfumerDataStore.getPerfumers(pageNum)
    }

    override suspend fun getPerfumer(perfumerId: Int): DataResponseDto<Any> {
        return perfumerDataStore.getPerfumer(perfumerId)
    }
}