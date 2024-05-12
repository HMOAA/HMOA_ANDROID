package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.FCMTokenSaveRequestDto

interface FcmRepository {
    suspend fun deleteFcmToken(): ResultResponse<Any>

    suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String>
}