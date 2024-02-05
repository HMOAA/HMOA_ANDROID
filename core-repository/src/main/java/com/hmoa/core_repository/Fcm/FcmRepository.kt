package com.hmoa.core_repository.Fcm

import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface FcmRepository {
    fun deleteFcmToken() : DataResponseDto<Any>

    fun saveFcmToken(fcmTokenSaveRequest : FCMTokenSaveRequestDto) : DataResponseDto<Any>
}