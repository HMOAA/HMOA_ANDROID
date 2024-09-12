package com.hmoa.core_network.service

import com.hmoa.core_model.request.CancelBootpayRequestDto
import com.hmoa.core_model.request.ConfirmBootpayRequestDto
import com.hmoa.core_model.response.BootpayOrderResultData
import com.hmoa.core_model.response.DataResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BootpayService {
    @POST("/bootpay/confirm")
    suspend fun postConfirm(
        @Body requestDto: ConfirmBootpayRequestDto
    ): ApiResponse<DataResponseDto<BootpayOrderResultData>>

    @POST("/bootpay/cancel")
    suspend fun postCancel(
        @Body requestDto: CancelBootpayRequestDto
    ): ApiResponse<DataResponseDto<Any>>
}