package com.hmoa.core_datastore.Fcm

import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface FcmDataStore {

    suspend fun deleteFcmToken() : DataResponseDto<Any>

    suspend fun saveFcmToken(fcmTokenSaveRequest : FCMTokenSaveRequestDto) : DataResponseDto<Any>

}