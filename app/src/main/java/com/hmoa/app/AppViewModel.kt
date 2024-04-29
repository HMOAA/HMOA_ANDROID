package com.hmoa.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.FcmRepository
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val fcmRepository : FcmRepository
) : ViewModel() {
    private var _authTokenState = MutableStateFlow<String?>(null)
    var authTokenState = _authTokenState
    private var _rememberedTokenState = MutableStateFlow<String?>(null)
    var rememberedTokenState = _rememberedTokenState

    init {
        initializeRoute()
    }

    fun initializeRoute() {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.getAuthToken().collectLatest {
                _authTokenState.value = it
            }
            loginRepository.getRememberedToken().collectLatest {
                _rememberedTokenState.value = it
            }
        }
    }

    fun saveFcmToken(fcmToken : String){
        viewModelScope.launch(Dispatchers.IO){
            val requestDto = FCMTokenSaveRequestDto(fcmToken)
            try{
                fcmRepository.saveFcmToken(requestDto)
            } catch(e : Exception){
                Log.e("TAG TEST", "Error : ${e.message}")
            }
        }
    }
}