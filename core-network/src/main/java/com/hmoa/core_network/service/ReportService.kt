package com.hmoa.core_network.service

import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {
    @POST("/report/community")
    suspend fun postReportCommunity(
        @Body dto: TargetRequestDto
    ): DataResponseDto<Any?>

    @POST("/report/communityComment")
    suspend fun postReportCommunityComment(
        @Body dto: TargetRequestDto
    ): DataResponseDto<Any>
    @POST("/report/perfumeComment")
    fun postReportPercumeComment(
        @Body dto: TargetRequestDto
    ): DataResponseDto<Any>
}