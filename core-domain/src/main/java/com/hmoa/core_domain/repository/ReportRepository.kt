package com.hmoa.core_domain.repository

import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface ReportRepository {
    fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any>
    fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any>
    fun reportPerfumeComment(dto: TargetRequestDto): DataResponseDto<Any>
}