package com.hmoa.core_network.service

import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportService {
    @POST("/report/community")
    suspend fun postReportCommunity(
        @Body dto: TargetRequestDto
    ): ApiResponse<DataResponseDto<Any>>

    @POST("/report/communityComment")
    suspend fun postReportCommunityComment(
        @Body dto: TargetRequestDto
    ): ApiResponse<DataResponseDto<Any>>
    @POST("/report/perfumeComment")
    suspend fun postReportPerfumeComment(@Body dto: TargetRequestDto): ApiResponse<DataResponseDto<Any?>>
    @POST("/report/hbti-review/{reviewId}")
    suspend fun postReportReview(@Path("reviewId") reviewId: Int): ApiResponse<DataResponseDto<Any>>
}