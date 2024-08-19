package com.hmoa.core_datastore.Fcm

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_model.response.AlarmResponse
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.FcmService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FcmRemoteDataStoreImpl @Inject constructor(
    private val fcmDataService: FcmService
) : FcmRemoteDataStore {

    override suspend fun deleteFcmToken(): ResultResponse<Any> {
        var result = ResultResponse<Any>()
        fcmDataService.deleteFcmToken().suspendOnSuccess {
            result.data = this.data.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
    override suspend fun getFcmList(): ResultResponse<DataResponseDto<List<AlarmResponse>>> {
        var result = ResultResponse<DataResponseDto<List<AlarmResponse>>>()
        fcmDataService.getAlarmList().suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
    override suspend fun checkAlarm(alarmId: Int): ResultResponse<DataResponseDto<Any>> {
        var result = ResultResponse<DataResponseDto<Any>>()
        fcmDataService.checkAlarm(alarmId).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
    override suspend fun saveFcmToken(fcmTokenSaveRequest: FCMTokenSaveRequestDto): ResultResponse<String> {
        var result = ResultResponse<String>()
        fcmDataService.saveFcmToken(fcmTokenSaveRequest).suspendOnSuccess {
            result.data = this.data.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
}