package com.hmoa.core_datastore.Report

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.ReportService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ReportDataStoreImpl @Inject constructor(private val reportService: ReportService) : ReportDataStore {
    override suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?> {
        return reportService.postReportCommunity(dto)
    }

    override suspend fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any> {
        return reportService.postReportCommunityComment(dto)
    }

    override suspend fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>> {
        val result = ResultResponse<DataResponseDto<Any?>>()
        reportService.postReportPerfumeComment(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun reportReview(reviewId: Int): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        reportService.postReportReview(reviewId).suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }
}