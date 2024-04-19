package com.hmoa.core_datastore.Report

import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.ReportService
import javax.inject.Inject

class ReportDataStoreImpl @Inject constructor(private val reportService: ReportService) : ReportDataStore {
    override suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?> {
        return reportService.postReportCommunity(dto)
    }

    override suspend fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any> {
        return reportService.postReportCommunityComment(dto)
    }

    override fun reportPerfumeComment(dto: TargetRequestDto): DataResponseDto<Any> {
        return reportService.postReportPercumeComment(dto)
    }
}