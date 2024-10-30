package com.hmoa.feature_userinfo.viewModel

import android.app.Application
import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.absolutePath
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.request.NickNameRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val application: Application,
    private val memberRepository: MemberRepository
) : ViewModel() {
    private val context = application.applicationContext

    private val _nickname = MutableStateFlow<String?>(null)
    val nickname get() = _nickname.asStateFlow()

    private val _profileImg = MutableStateFlow<String?>(null)
    val profileImg get() = _profileImg.asStateFlow()

    private val _isDuplicated = MutableStateFlow(false)
    private val _isProfileUpdate = MutableStateFlow(false)

    val isEnabled = nickname.map {
        if (uiState.value != EditProfileUiState.Loading && it == null) {
            errState.update { "Nickname is NULL" }
        }
        (it != "" && it != baseNickname)
    }

    val isEnabledBtn = combine(
        _isDuplicated,
        _isProfileUpdate,
    ) { isDup, isUpdate ->
        isDup || isUpdate
    }

    private val errState = MutableStateFlow<String?>(null)

    private var baseNickname = ""

    val uiState: StateFlow<EditProfileUiState> = errState.map {
        val result = memberRepository.getMember()
        if (result.errorMessage != null) {
            throw Exception(result.errorMessage!!.message)
        }
        if (errState.value != null) {
            throw Exception(errState.value)
        }
        result.data!!
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> EditProfileUiState.Loading
            is Result.Success -> {
                val data = result.data
                baseNickname = data.nickname
                _nickname.update { data.nickname }
                _profileImg.update { data.memberImageUrl }
                EditProfileUiState.Success
            }

            is Result.Error -> {
                EditProfileUiState.Error(if (result.exception is Exception) result.exception.toString() else errState.value.toString())
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = EditProfileUiState.Loading
    )

    //nick name 업데이트
    fun updateNickname(newNick: String) {
        _nickname.update { newNick }
        _isDuplicated.update { false }
    }

    //profile 업데이트
    fun updateProfile(newProfile: String) {
        _profileImg.update { newProfile }
        _isProfileUpdate.update { true }
    }

    //nickname 중복 검사
    fun checkNicknameDup(nick: String) {
        val requestDto = NickNameRequestDto(nick)
        viewModelScope.launch {
            val result = memberRepository.postExistsNickname(requestDto)
            if (result.errorMessage != null) {
                if (result.errorMessage!!.code == "DUPLICATED_NICKNAME"){
                    _isDuplicated.update{false}
                } else {
                    errState.update { result.errorMessage!!.message }
                }
            }
            _isDuplicated.update { !result.data!! }
        }
    }

    //remote 정보 update
    fun saveInfo(context: Context) {
        val path = absolutePath(context, profileImg.value!!.toUri()) ?: return
        val file = File(path)
        val requestDto = NickNameRequestDto(nickname.value)
        viewModelScope.launch {
            if (profileImg.value != null) {
                val resultProfile = memberRepository.postProfilePhoto(file)
                val resultNickname = memberRepository.updateNickname(requestDto)

                if (resultProfile.errorMessage != null) {
                    errState.update { resultProfile.errorMessage!!.message }
                }
                if (resultNickname.errorMessage != null) {
                    errState.update { resultNickname.errorMessage!!.message }
                }
            }
        }
    }
}

sealed interface EditProfileUiState {
    data object Loading : EditProfileUiState
    data object Success : EditProfileUiState
    data class Error(
        val message: String
    ) : EditProfileUiState
}