package com.hyangmoa.core_network.service

import com.hyangmoa.core_model.request.TargetRequestDto
import com.hyangmoa.core_model.response.DataResponseDto
import retrofit2.http.Body
import com.skydoves.sandwich.ApiResponse
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
    suspend fun postReportPerfumeComment(@Body dto: TargetRequestDto): ApiResponse<DataResponseDto<Any?>>
}