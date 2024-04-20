package com.hmoa.core_datastore.Report

import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface ReportDataStore {
    suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?>
    suspend fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any>
    fun reportPerfumeComment(dto: TargetRequestDto): DataResponseDto<Any>
}