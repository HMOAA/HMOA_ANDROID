package com.hmoa.feature_authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.usecase.GetNicknameUseCase
import com.hmoa.core_domain.usecase.PostSignupUseCase
import com.hmoa.core_domain.usecase.SaveAuthAndRememberedTokenUseCase
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PickPersonalInfoViewmodel @Inject constructor(
    private val getNickname: GetNicknameUseCase,
    private val postSignupInfo: PostSignupUseCase,
    private val loginRepository: LoginRepository,
    private val saveAuthAndRememberedToken: SaveAuthAndRememberedTokenUseCase
) : ViewModel() {
    private val _birthYearState = MutableStateFlow<Int?>(null)
    var birthYearState = _birthYearState.asStateFlow()
    private val _sexState = MutableStateFlow("여성")
    var sexState = _sexState.asStateFlow()
    private val _isPostComplete = MutableStateFlow(false)
    var isPostComplete = _isPostComplete.asStateFlow()

    private suspend fun getSavedNickname(): String? {
        return getNickname()
    }

    fun saveBirthYear(value: Int) {
        _birthYearState.update { value }
    }

    fun saveSex(value: String) {
        _sexState.update { value }
    }

    fun signup(loginProvider: String, birthYear: Int?, sex: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var token: String? = ""
            when (loginProvider) {
                Provider.GOOGLE.name -> {
                    loginRepository.getGoogleAccessToken().collectLatest { token = it }
                    if (token != null) {
                        postAccessToken(token!!, Provider.GOOGLE, birthYear, sex)
                    }
                }

                Provider.KAKAO.name -> {
                    loginRepository.getKakaoAccessToken().collectLatest { token = it }
                    Log.d("PickPersonalInfoViewmodel", "kakaoToken : ${token}")
                    if (token != null) {
                        postAccessToken(token!!, Provider.GOOGLE, birthYear, sex)
                    }
                }
            }
        }
    }

    fun getGoogleAccessToken() {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.getGoogleAccessToken().onEmpty { }
                .collectLatest {
                    Log.d("PickPersonalInfoViewmodel", "googleToken-in flow : ${it}")
                }
        }
    }

    suspend fun postAccessToken(token: String, loginProvider: Provider, birthYear: Int?, sex: String) {
        viewModelScope.launch {
            flow {
                val result = loginRepository.postOAuth(OauthLoginRequestDto(token), provider = loginProvider)
                if (result.errorMessage != null) {
                    throw Exception(result.errorMessage!!.message)
                } else {
                    emit(result.data)
                }
            }.asResult()
                .collectLatest {
                    when (it) {
                        is Result.Success -> {
                            val authToken = it.data?.authToken
                            val rememberedToken = it.data?.rememberedToken
                            if (it.data != null && authToken != null && rememberedToken != null) {
                                saveAuthAndRememberedToken(authToken, rememberedToken)
                                postSignup(birthYear, sex)
                            }

                        }

                        is Result.Loading -> {
                            LoginUiState.Loading
                        }

                        is Result.Error -> {}
                    }
                }
        }
    }

    fun postSignup(birthYear: Int?, sex: String) {
        if (birthYear == null) return

        val age = mapBirthYearToAge(birthYear)
        val sex = mapSexToBoolean(sex)
        var nickname: String? = ""

        viewModelScope.launch { nickname = getSavedNickname() }

        if (!isAvailableToSignup(nickname, age)) return

        viewModelScope.launch {
            postSignupInfo(age, sex, nickname = nickname!!).asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            _isPostComplete.update { true }
                        }

                        is Result.Loading -> {}
                        is Result.Error -> {}
                    }
                }
        }
    }

    fun isAvailableToSignup(nickname: String?, age: Int): Boolean {
        if (isNicknameNotNull(nickname) && isAvailableAge(age)) return true
        return false
    }

    private fun mapBirthYearToAge(birthYear: Int): Int {
        val calendar = Calendar.getInstance()
        val age = calendar.get(Calendar.YEAR) - birthYear
        return age
    }

    private fun isNicknameNotNull(nickname: String?): Boolean {
        if (nickname == null) return false
        return true
    }

    private fun isAvailableAge(age: Int): Boolean {
        if (age > 0) return true
        return false
    }

    private fun mapSexToBoolean(sex: String): Boolean {
        if (sex == "여성") return false
        return true
    }
}

