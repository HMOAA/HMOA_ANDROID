package com.hmoa.feature_authentication.viewmodel

import androidx.lifecycle.ViewModel
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.usecase.GetNicknameUseCase
import com.hmoa.core_domain.usecase.PostSignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PickPersonalInfoViewmodel @Inject constructor(
    private val getNickname: GetNicknameUseCase,
    private val postSignupInfo: PostSignupUseCase,
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

    suspend fun postSignup(birthYear: Int?, sex: String) {
        if (birthYear == null) return
        val age = mapBirthYearToAge(birthYear)
        val sex = mapSexToBoolean(sex)
        val nickname = getSavedNickname()
        if (!isAvailableToSignup(nickname, age)) return
        postSignupInfo(age, sex, nickname = nickname!!).asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _isPostComplete.update { true }
                    }

                    is Result.Loading -> {}//TODO("로딩화면")
                    is Result.Error -> {}//TODO()
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