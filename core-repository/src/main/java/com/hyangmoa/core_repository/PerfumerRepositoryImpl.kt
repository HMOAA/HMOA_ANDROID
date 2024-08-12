package com.hyangmoa.core_repository

import ResultResponse
import com.hyangmoa.core_datastore.Perfumer.PerfumerDataStore
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.PerfumerDescResponseDto
import javax.inject.Inject

class PerfumerRepositoryImpl @Inject constructor(private val perfumerDataStore: PerfumerDataStore) :
    com.hyangmoa.core_domain.repository.PerfumerRepository {
    override suspend fun getPerfumers(pageNum: String): DataResponseDto<Any> {
        return perfumerDataStore.getPerfumers(pageNum)
    }

    override suspend fun getPerfumer(perfumerId: Int): ResultResponse<DataResponseDto<PerfumerDescResponseDto>> {
        return perfumerDataStore.getPerfumer(perfumerId)
    }
}