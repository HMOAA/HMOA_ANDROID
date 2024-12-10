package com.hmoa.core_datastore.Report

import ResultResponse
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface ReportDataStore {
    suspend fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>>
    suspend fun reportCommunity(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any>>
    suspend fun reportCommunityComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any>>
    suspend fun reportReview(reviewId: Int): ResultResponse<DataResponseDto<Any>>
}