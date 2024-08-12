package com.hyangmoa.core_domain.repository

import ResultResponse
import com.hyangmoa.core_model.request.TargetRequestDto
import com.hyangmoa.core_model.response.DataResponseDto

interface ReportRepository {
    suspend fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>>
    suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?>
    suspend fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any>
    }