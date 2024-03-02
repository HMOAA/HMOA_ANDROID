package com.hmoa.core_domain.repository

import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface FcmRepository {
    suspend fun deleteFcmToken(): DataResponseDto<Any>

    suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): DataResponseDto<Any>
}