package com.hmoa.core_datastore.Report

import ResultResponse
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.ReportService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class ReportDataStoreImpl @Inject constructor(private val reportService: ReportService) : ReportDataStore {
    override fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?> {
        return reportService.postReportCommunity(dto)
    }

    override fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any?> {
        return reportService.postReportCommunityComment(dto)
    }

    suspend override fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>> {
        val result = ResultResponse<DataResponseDto<Any?>>()
        reportService.postReportPerfumeComment(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }
}