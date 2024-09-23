package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Bootpay.BootpayDataStore
import com.hmoa.core_domain.repository.BootpayRepository
import com.hmoa.core_model.request.CancelBootpayRequestDto
import com.hmoa.core_model.request.ConfirmBootpayRequestDto
import com.hmoa.core_model.response.BootpayOrderResultData
import com.hmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class BootpayRepositoryImpl @Inject constructor(
    private val bootpayDataStore: BootpayDataStore
): BootpayRepository {
    override suspend fun postConfirm(requestDto: ConfirmBootpayRequestDto): ResultResponse<DataResponseDto<BootpayOrderResultData>> {
        return bootpayDataStore.postConfirm(requestDto)
    }

    override suspend fun postCancel(requestDto: CancelBootpayRequestDto): ResultResponse<DataResponseDto<Any>> {
        return bootpayDataStore.postCancel(requestDto)
    }

    override suspend fun deleteOrder(orderId: Int): ResultResponse<DataResponseDto<Any>> {
        return bootpayDataStore.deleteOrder(orderId)
    }
}