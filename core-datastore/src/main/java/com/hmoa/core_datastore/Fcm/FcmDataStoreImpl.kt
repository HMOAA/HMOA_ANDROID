package com.hmoa.core_datastore.Fcm

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import com.hmoa.core_network.service.FcmService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FcmDataStoreImpl @Inject constructor(
    private val fcmDataService: FcmService
) : FcmDataStore {

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