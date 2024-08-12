package com.hyangmoa.core_datastore.Report

import ResultResponse
import com.hyangmoa.core_model.data.ErrorMessage
import com.hyangmoa.core_model.request.TargetRequestDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_network.service.ReportService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ReportDataStoreImpl @Inject constructor(private val reportService: ReportService) : ReportDataStore {
    override suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?> {
        return reportService.postReportCommunity(dto)
    }

    override suspend fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any> {
        return reportService.postReportCommunityComment(dto)
    }

    suspend override fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>> {
        val result = ResultResponse<DataResponseDto<Any?>>()
        reportService.postReportPerfumeComment(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
}