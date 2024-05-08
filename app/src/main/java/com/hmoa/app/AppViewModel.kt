package com.hmoa.app

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

    suspend fun authToken(): Flow<String?> {
        return loginRepository.getAuthToken()
    }

    suspend fun rememberedToken(): Flow<String?> {
        return loginRepository.getRememberedToken()
    }

    suspend fun getFcmToken(): Flow<String?> {
        return loginRepository.getFcmToken()
    }
    fun saveFcmToken(fcmToken : String){
        viewModelScope.launch{
            loginRepository.saveFcmToken(fcmToken)
        }
    }

    fun postFcmToken(fcmToken : String){
        viewModelScope.launch{
            val requestDto = FCMTokenSaveRequestDto(fcmToken)
            fcmRepository.saveFcmToken(requestDto)
        }
    }
}