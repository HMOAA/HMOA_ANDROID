package com.hyangmoa.feature_authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyangmoa.core_common.Result
import com.hyangmoa.core_common.asResult
import com.hyangmoa.core_domain.repository.LoginRepository
import com.hyangmoa.core_domain.usecase.GetNicknameUseCase
import com.hyangmoa.core_domain.usecase.PostSignupUseCase
import com.hyangmoa.core_domain.usecase.SaveAuthAndRememberedTokenUseCase
import com.hyangmoa.core_model.Provider
import com.hyangmoa.core_model.request.OauthLoginRequestDto
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
    private var _birthYearState = MutableStateFlow<Int?>(null)
    val birthYearState = _birthYearState.asStateFlow()
    private var _sexState = MutableStateFlow("여성")
    val sexState = _sexState.asStateFlow()
    private var _isPostComplete = MutableStateFlow(false)
    val isPostComplete = _isPostComplete.asStateFlow()
    private val googleAccessToken = MutableStateFlow<String?>(null)
    private val kakaoAccessToken = MutableStateFlow<String?>(null)

    fun getSocialLoginAccessToken(loginProvider: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (loginProvider == Provider.GOOGLE.name) {
                getGoogleAccessToken()
            } else if (loginProvider == Provider.KAKAO.name) {
                getKakaoAccessToken()
            }
        }
    }

    suspend fun getGoogleAccessToken() {
        loginRepository.getGoogleAccessToken().onEmpty { }
            .collectLatest {
                googleAccessToken.value = it
            }
    }

    suspend fun getKakaoAccessToken() {
        loginRepository.getKakaoAccessToken().onEmpty { }
            .collectLatest {
                kakaoAccessToken.value = it
            }
    }

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
            when (loginProvider) {
                Provider.GOOGLE.name -> {
                    if (googleAccessToken.value != null) {
                        postAccessToken(googleAccessToken.value!!, Provider.GOOGLE, birthYear, sex)
                    }
                }

                Provider.KAKAO.name -> {
                    if (kakaoAccessToken.value != null) {
                        postAccessToken(kakaoAccessToken.value!!, Provider.KAKAO, birthYear, sex)
                    }
                }
            }
        }
    }

    suspend fun postAccessToken(token: String, loginProvider: Provider, birthYear: Int?, sex: String) {
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

