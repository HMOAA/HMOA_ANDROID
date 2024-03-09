package com.hmoa.feature_authentication.viewmodel

import androidx.lifecycle.ViewModel
import com.hmoa.core_domain.usecase.GetSignupInfoUseCase
import com.hmoa.core_domain.usecase.PostSignupInfoUseCase
import com.hmoa.core_domain.usecase.SaveSignupInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PickPersonalInfoViewmodel @Inject constructor(
    private val getSignupInfoUseCase: GetSignupInfoUseCase,
    private val saveSignupInfoUseCase: SaveSignupInfoUseCase,
    private val postSignupInfoUseCase: PostSignupInfoUseCase
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)


    suspend fun getSavedNickname(): String? {
        return getSignupInfoUseCase.getNickName()
    }

    suspend fun getSavedAge(): String? {
        return getSignupInfoUseCase.getAge()
    }

    suspend fun getSavedSex(): String? {
        return getSignupInfoUseCase.getSex()
    }

    fun saveAge(value: String) {
        scope.launch { saveSignupInfoUseCase.saveAge(value) }
    }

    fun saveSex(value: String) {
        scope.launch { saveSignupInfoUseCase.saveSex(value) }
    }

    fun postSignup(birthYear: String, sex: String) {
        scope.launch {
            val age = mapBirthYearToAge(birthYear)
            val sex = mapSexToBoolean(sex)
            val nickname = getSavedNickname()
            if (isNicknameNotNull(nickname) && isAvailableAge(age)) {
                postSignupInfoUseCase(age, sex, nickname = nickname!!)
            }
        }
    }

    fun mapBirthYearToAge(birthYear: String): Int {
        val age = Calendar.YEAR - birthYear.toInt()
        return age
    }

    fun isNicknameNotNull(nickname: String?): Boolean {
        if (nickname == null) return false
        return true
    }

    fun isAvailableAge(age: Int): Boolean {
        if (age > 0) return true
        return false
    }

    fun mapSexToBoolean(sex: String): Boolean {
        if (sex == "여성") return false
        return true
    }
}