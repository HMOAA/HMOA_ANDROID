package com.hmoa.core_network.service

import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {
    @POST("/report/community")
    fun postReportCommunity(@Body dto: TargetRequestDto): DataResponseDto<Any?>

    @POST("/report/communityComment")
    fun postReportCommunityComment(@Body dto: TargetRequestDto): DataResponseDto<Any?>

    @POST("/report/perfumeComment")
    suspend fun postReportPerfumeComment(@Body dto: TargetRequestDto): ApiResponse<DataResponseDto<Any?>>
}