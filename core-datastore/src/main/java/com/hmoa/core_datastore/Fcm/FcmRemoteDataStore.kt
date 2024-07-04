package com.hmoa.core_datastore.Fcm

import ResultResponse
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.AlarmResponse
import com.hmoa.core_model.response.DataResponseDto

interface FcmRemoteDataStore {

    suspend fun deleteFcmToken(): ResultResponse<Any>
    suspend fun getFcmList() : ResultResponse<DataResponseDto<List<AlarmResponse>>>
    suspend fun checkAlarm(alarmId : Int) : ResultResponse<DataResponseDto<Any>>
    suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String>

}