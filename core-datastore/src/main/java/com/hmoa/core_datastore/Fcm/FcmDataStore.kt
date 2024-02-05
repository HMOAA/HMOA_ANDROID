package com.hmoa.core_datastore.Fcm

import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto

interface FcmDataStore {

    fun deleteFcmToken() : DataResponseDto<Any>

    fun saveFcmToken(fcmTokenSaveRequest : FCMTokenSaveRequestDto) : DataResponseDto<Any>

}