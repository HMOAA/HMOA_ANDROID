package com.hmoa.feature_userinfo.viewModel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
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
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val application : Application,
    private val memberRepository : MemberRepository
) : ViewModel() {
    private val context = application.applicationContext

    private val _nickname = MutableStateFlow<String?>(null)
    val nickname get() = _nickname.asStateFlow()

    private val _profileImg = MutableStateFlow<String?>(null)
    val profileImg get() = _profileImg.asStateFlow()

    private val _isDuplicated = MutableStateFlow(false)
    private val _isProfileUpdate = MutableStateFlow(false)

    val isEnabled = nickname.map{
        if (uiState.value != EditProfileUiState.Loading && it == null){
            errState.update{ "Nickname is NULL" }
        }
        (it != "" && it != baseNickname)
    }

    val isEnabledBtn = combine(
        _isDuplicated,
        _isProfileUpdate,
    ){ isDup, isUpdate ->
        isDup || isUpdate
    }

    private val errState = MutableStateFlow<String?>(null)

    private var baseNickname = ""

    val uiState : StateFlow<EditProfileUiState> = errState.map{
        val result = memberRepository.getMember()
        if (result.exception is Exception){
            throw result.exception!!
        }
        if (errState.value != null){
            throw Exception(errState.value)
        }
        result.data!!
    }.asResult().map{result ->
        when(result) {
            Result.Loading -> EditProfileUiState.Loading
            is Result.Success -> {
                val data = result.data
                baseNickname = data.nickname
                _nickname.update{ data.nickname }
                _profileImg.update{ data.memberImageUrl }
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
    fun updateNickname(newNick : String) {
        _nickname.update{newNick}
        _isDuplicated.update{false}
    }

    //profile 업데이트
    fun updateProfile(newProfile : String) {
        _profileImg.update{ newProfile }
        _isProfileUpdate.update { true }
    }

    //nickname 중복 검사
    fun checkNicknameDup(nick : String){
        val requestDto = NickNameRequestDto(nick)
        viewModelScope.launch{
            val result = memberRepository.postExistsNickname(requestDto)
            if (result.exception is Exception){
                errState.update{ result.exception.toString() }
            }
            _isDuplicated.update{ !result.data!! }
        }
    }

    //remote 정보 update
    fun saveInfo(){
        val path = absolutePath(profileImg.value!!.toUri()) ?: ""
        val file = File(path)
        val requestDto = NickNameRequestDto(nickname.value)
        viewModelScope.launch{
            if (profileImg.value != null){
                val resultProfile = memberRepository.postProfilePhoto(file)
                val resultNickname = memberRepository.updateNickname(requestDto)

                if (resultProfile.exception is Exception){
                    errState.update{ resultProfile.exception.toString() }
                }
                if (resultNickname.exception is Exception){
                    errState.update{ resultNickname.exception.toString() }
                }
            }
        }
    }

    private fun absolutePath(uri : Uri) : String? {
        val contentResolver = context.contentResolver

        val filePath = (context.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
        val file = File(filePath)

        try{
            val inputStream = contentResolver.openInputStream(uri) ?: return null

            val outputStream = FileOutputStream(file)

            val buf = ByteArray(1024)
            var len : Int
            while (inputStream.read(buf).also {len = it} > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore : Exception) {
            return null
        }
        return file.absolutePath
    }
}

sealed interface EditProfileUiState{
    data object Loading : EditProfileUiState
    data object Success : EditProfileUiState
    data class Error(
        val message : String
    ) : EditProfileUiState
}