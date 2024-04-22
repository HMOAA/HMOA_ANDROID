package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface ReportRepository {
    fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?>
    fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any?>
    suspend fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>>
}