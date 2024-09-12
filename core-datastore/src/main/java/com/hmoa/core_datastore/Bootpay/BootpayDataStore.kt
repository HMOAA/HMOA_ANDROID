package com.hmoa.core_datastore.Bootpay

import ResultResponse
import com.hmoa.core_model.request.CancelBootpayRequestDto
import com.hmoa.core_model.request.ConfirmBootpayRequestDto
import com.hmoa.core_model.response.BootpayOrderResultData
import com.hmoa.core_model.response.DataResponseDto

interface BootpayDataStore {
    suspend fun postConfirm(requestDto: ConfirmBootpayRequestDto): ResultResponse<DataResponseDto<BootpayOrderResultData>>
    suspend fun postCancel(requestDto: CancelBootpayRequestDto): ResultResponse<DataResponseDto<Any>>
}