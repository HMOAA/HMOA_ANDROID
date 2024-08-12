package com.hyangmoa.core_repository

import ResultResponse
import com.hyangmoa.core_datastore.Report.ReportDataStore
import com.hyangmoa.core_domain.repository.ReportRepository
import com.hyangmoa.core_model.request.TargetRequestDto
import com.hyangmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(private val reportDataStore: ReportDataStore) : ReportRepository {
    override suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?> {
        return reportDataStore.reportCommunity(dto)
    }

    override suspend fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any> {
        return reportDataStore.reportCommunityComment(dto)
    }

    suspend override fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>> {
        return reportDataStore.reportPerfumeComment(dto)
    }

}