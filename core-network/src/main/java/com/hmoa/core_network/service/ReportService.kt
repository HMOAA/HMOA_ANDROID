package com.hmoa.core_network.service

import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto
import retrofit2.http.POST

interface ReportService {
    @POST("/report/community")
    fun postReportCommunity(dto: TargetRequestDto): DataResponseDto<Any>
    fun postReportCommunityComment(dto: TargetRequestDto): DataResponseDto<Any>
    fun postReportPercumeComment(dto: TargetRequestDto): DataResponseDto<Any>
}