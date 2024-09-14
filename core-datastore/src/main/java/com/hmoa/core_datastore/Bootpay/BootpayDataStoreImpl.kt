package com.hmoa.core_datastore.Bootpay

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.CancelBootpayRequestDto
import com.hmoa.core_model.request.ConfirmBootpayRequestDto
import com.hmoa.core_model.response.BootpayOrderResultData
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.BootpayService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BootpayDataStoreImpl @Inject constructor(
    private val bootpayService: BootpayService
): BootpayDataStore {
    override suspend fun postConfirm(requestDto: ConfirmBootpayRequestDto): ResultResponse<DataResponseDto<BootpayOrderResultData>> {
        val result = ResultResponse<DataResponseDto<BootpayOrderResultData>>()
        bootpayService.postConfirm(requestDto).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun postCancel(requestDto: CancelBootpayRequestDto): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        bootpayService.postCancel(requestDto).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }
}