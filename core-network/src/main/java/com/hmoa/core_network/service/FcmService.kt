package com.hmoa.core_network.service

import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface FcmService {
    suspend fun deleteFcmToken(): DataResponseDto<Any>
    suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): DataResponseDto<Any>
}