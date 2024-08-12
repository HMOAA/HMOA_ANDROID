package com.hyangmoa.core_datastore.Report

import ResultResponse
import com.hyangmoa.core_model.request.TargetRequestDto
import com.hyangmoa.core_model.response.DataResponseDto

interface ReportDataStore {
    suspend fun reportPerfumeComment(dto: TargetRequestDto): ResultResponse<DataResponseDto<Any?>>
    suspend fun reportCommunity(dto: TargetRequestDto): DataResponseDto<Any?>
    suspend fun reportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any>
    }