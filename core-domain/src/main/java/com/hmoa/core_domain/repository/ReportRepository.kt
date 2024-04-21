package com.hmoa.core_domain.repository

import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface ReportRepository {
    suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?>
    suspend fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any>
    fun reportPerfumeComment(dto: TargetRequestDto): DataResponseDto<Any>
}