package com.hmoa.core_datastore.Report

import ResultResponse
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.ReportService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class ReportDataStoreImpl @Inject constructor(
    private val reportService: ReportService,
    private val authenticator: Authenticator
) : ReportDataStore {
    override suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?> {
        return reportService.postReportCommunity(dto)
    }

    override suspend fun reportCommunityComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        reportService.postReportCommunityComment(dto).suspendOnError{
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    reportService.postReportCommunityComment(dto).suspendOnSuccess { result.data = this.data }
                }
            )
        }.suspendOnSuccess{
            result.data = this.data
        }
        return result
    }

    suspend override fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>> {
        val result = ResultResponse<DataResponseDto<Any?>>()
        reportService.postReportPerfumeComment(dto).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    reportService.postReportPerfumeComment(dto).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}