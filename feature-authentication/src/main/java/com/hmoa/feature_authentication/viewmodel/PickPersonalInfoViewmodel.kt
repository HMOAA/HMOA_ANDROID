package com.hmoa.feature_authentication.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.ui.BaseViewModel
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.SignupRepository
import com.hmoa.core_domain.usecase.PostSignupUseCase
import com.hmoa.core_domain.usecase.SaveAuthAndRememberedTokenUseCase
import com.hmoa.core_model.Provider
import com.hmoa.core_model.request.OauthLoginRequestDto
import com.hmoa.feature_authentication.contract.PickPersonalInfoEffect
import com.hmoa.feature_authentication.contract.PickPersonalInfoEvent
import com.hmoa.feature_authentication.contract.PickPersonalInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PickPersonalInfoViewmodel @Inject constructor(
    private val signupRepository: SignupRepository,
    private val postSignupInfo: PostSignupUseCase,
    private val loginRepository: LoginRepository,
    private val saveAuthAndRememberedToken: SaveAuthAndRememberedTokenUseCase
) : BaseViewModel<PickPersonalInfoEvent, PickPersonalInfoState, PickPersonalInfoEffect>() {
    val SEX = listOf("여성", "남성")

    override fun createInitialState(): PickPersonalInfoState {
        return PickPersonalInfoState(
            isAvailableToSignup = false,
            birthYear = null,
            sex = SEX[0],
            token = null
        )
    }

    override fun handleEvent(event: PickPersonalInfoEvent) {
        viewModelScope.launch {
            when (event) {
                is PickPersonalInfoEvent.FinishOnBoarding -> {
                    Log.d("FinishOnBoarding", "???")
                    signup(
                        loginProvider = event.loginProvider,
                        birthYear = uiState.value.birthYear,
                        sex = uiState.value.sex
                    )
                }

                is PickPersonalInfoEvent.SaveBirthYear -> {
                    setState { copy(birthYear = event.birthYear) }
                    isAvailableNextButton()
                }

                is PickPersonalInfoEvent.SaveSex -> {
                    setState { copy(sex = event.sex) }
                }
            }
        }
    }

    fun isAvailableNextButton() {
        if (uiState.value.birthYear != null) {
            setState { copy(isAvailableToSignup = true) }
        }
    }

    suspend fun getGoogleAccessToken(): String? {
        var token: String? = null
        loginRepository.getGoogleAccessToken().onEmpty { }.collectLatest {
            Log.d("getGoogleAccessTokne PPIViewmodel", "token:${token}")
            token = it
        }
        return token
    }

    suspend fun getKakaoAccessToken(): String? {
        var token: String? = null
        loginRepository.getKakaoAccessToken().onEmpty { }.collectLatest { token = it }
        return token
    }

    private suspend fun getSavedNickname(): String? {
        return signupRepository.getNickname()
    }

    suspend fun signup(loginProvider: String, birthYear: Int?, sex: String?) {
        Log.d(
            "signup",
            "loginProvider:${loginProvider}, 비교값: ${Provider.GOOGLE.name}, sex:${sex}, birthYear:${birthYear}"
        )
        when (loginProvider) {
            Provider.GOOGLE.name -> {
                val token = getGoogleAccessToken()
                Log.d("google token", "token:${token}")
                if (token != null && sex != null) {
                    postAccessToken(token, Provider.GOOGLE, birthYear, sex)
                }
            }

            Provider.KAKAO.name -> {
                val token = getKakaoAccessToken()
                Log.d("login token", "token:${token}")
                if (token != null && sex != null) {
                    postAccessToken(token, Provider.KAKAO, birthYear, sex)
                }
            }

            else -> {
                Log.d("loginProvider", "전달 값: ${loginProvider}, 구글 비교 값: ${Provider.GOOGLE.name}")
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
                            setEffect(PickPersonalInfoEffect.NavigateToHome)
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

