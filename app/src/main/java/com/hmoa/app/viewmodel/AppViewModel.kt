package com.hmoa.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.FcmRepository
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val fcmRepository: FcmRepository,
) : ViewModel() {
    val authTokenState = MutableStateFlow<String?>(null)
    suspend fun authTokenFlow(): Flow<String?> = loginRepository.getAuthToken()
    suspend fun rememberedTokenFlow(): Flow<String?> = loginRepository.getRememberedToken()
    suspend fun fcmTokenFlow(): Flow<String?> =
        fcmRepository.getLocalFcmToken().shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {
        getAuthToken()
    }

    fun delFcmToken() {
        viewModelScope.launch {
            if (authTokenState.value != null) {
                fcmRepository.deleteLocalFcmToken()
                fcmRepository.deleteRemoteFcmToken()
            }
        }
    }

    fun getAuthToken() {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.getAuthToken().onEmpty { }.collectLatest {
                authTokenState.update { it }
            }
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
    suspend fun getNotificationEnabled(): Flow<Boolean> =
        fcmRepository.getNotificationEnabled().shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    suspend fun saveNotificationEnabled(isEnabled: Boolean) =
        viewModelScope.launch { fcmRepository.saveNotificationEnabled(isEnabled) }

}
