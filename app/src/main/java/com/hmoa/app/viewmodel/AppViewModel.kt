package com.hmoa.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.FcmRepository
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val fcmRepository: FcmRepository,
) : ViewModel() {
    suspend fun authTokenFlow(): Flow<String?> = loginRepository.getAuthToken()
    suspend fun rememberedTokenFlow(): Flow<String?> = loginRepository.getRememberedToken()

    suspend fun fcmTokenFlow(): Flow<String?> = fcmRepository.getLocalFcmToken()

    fun delFcmToken() {
        viewModelScope.launch {
            fcmRepository.deleteLocalFcmToken()
            fcmRepository.deleteRemoteFcmToken()
        }
    }

    fun postFcmToken(fcmToken: String) {
        viewModelScope.launch {
            val requestDto = FCMTokenSaveRequestDto(fcmToken)
            fcmRepository.postRemoteFcmToken(requestDto)
        }
    }

    fun saveFcmToken(token: String) = viewModelScope.launch { fcmRepository.saveLocalFcmToken(token) }
    fun checkAlarm(id: Int) = viewModelScope.launch { fcmRepository.checkAlarm(id) }
    suspend fun getNotificationEnabled(): Flow<Boolean> = fcmRepository.getNotificationEnabled()

    suspend fun saveNotificationEnabled(isEnabled: Boolean) =
        viewModelScope.launch { fcmRepository.saveNotificationEnabled(isEnabled) }

}
