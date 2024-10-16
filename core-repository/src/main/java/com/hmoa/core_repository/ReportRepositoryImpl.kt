package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Report.ReportDataStore
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(private val reportDataStore: ReportDataStore) : ReportRepository {
    override suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?> {
        return reportDataStore.reportCommunity(dto)
    }

    override suspend fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any> {
        return reportDataStore.reportCommunityComment(dto)
    }

    override suspend fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>> {
        return reportDataStore.reportPerfumeComment(dto)
    }

    override suspend fun reportReview(reviewId: Int): ResultResponse<DataResponseDto<Any>> {
        return reportDataStore.reportReview(reviewId)
    }
}