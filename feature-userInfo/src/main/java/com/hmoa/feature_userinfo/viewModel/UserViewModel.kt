package com.example.feature_userinfo.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetAuthAndRememberedTokenUseCase
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: MemberRepository,
    private val getAuthAndRememberedTokenUseCase: GetAuthAndRememberedTokenUseCase,
    private val getUserInfoUseCase : GetMyUserInfoUseCase
) : ViewModel() {

    //Login 여부
    private val _isLogin = MutableStateFlow(false)
    val isLogin: StateFlow<Boolean>
        get() = _isLogin

    //nick name
    private val _nickname = MutableStateFlow("")
    val nickname : StateFlow<String> get() = _nickname

    //profile
    private val _profile = MutableStateFlow("")
    val profile : StateFlow<String> get() = _profile

    //gender
    private val _gender = MutableStateFlow("")
    val gender : StateFlow<String> get() = _gender

    //birth
    private val _birth = MutableStateFlow(0)
    val birth : StateFlow<Int> get() = _birth

    //provider
    private val _provider = MutableStateFlow("")
    val provider : StateFlow<String> get() = _provider

    //버튼 활성화
    private val _isEnabled = MutableStateFlow(false)
    val isEnabled get() = _isEnabled

    //중복 확인
    private val _isDuplicated = MutableStateFlow(false)
    val isDuplicated get() = _isDuplicated

    init {
        viewModelScope.launch{
            val tokens = getAuthAndRememberedTokenUseCase()
            if (tokens.first != null && tokens.second != null) {
                _isLogin.update{true}
            } else {
                _isLogin.update{false}
            }
        }

        //기본 nickname, profile, gender, birth 가져오기
        viewModelScope.launch(Dispatchers.IO){
            getUserInfoUseCase()
                .asResult()
                .map{ result ->
                    when(result) {
                        is Result.Success -> {
                            _birth.update {result.data.birth}
                            _gender.update {result.data.gender}
                            _profile.update {result.data.profile}
                            _nickname.update {result.data.nickname}
                            _provider.update {result.data.provider}
                        }
                        is Result.Loading -> {

                        }
                        is Result.Error -> {

                        }
                    }
                }

        }
    }

    val uiState : StateFlow<UserInfoUiState> = combine(
        nickname,
        profile,
        gender,
        birth,
        provider,
        UserInfoUiState::UserInfo
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = UserInfoUiState.Loading
    )

    //nickname 중복 확인 여부
    fun checkDuplicate(){
        viewModelScope.launch(Dispatchers.IO){
            val request = NickNameRequestDto(nickname.value)
            val result = repository.postExistsNickname(request)
            if (result) {
                //중복 검사 true면 괜찮다는 말이겠지..
                _isDuplicated.update {true}
                _isEnabled.update {true}
            } else {
                //중복 검사 false면 불가능이라는 뜻이겠지?
                _isDuplicated.update {false}
                _isEnabled.update {false}
            }
        }
    }

    //nickname update
    fun updateNickname(newNickname : String) {
        _nickname.update{newNickname}
    }

    //nickname update
    fun saveNickname(){
        viewModelScope.launch(Dispatchers.IO){
            val request = NickNameRequestDto(nickname = nickname.value)
            repository.updateNickname(request)
        }
        resetEnabled()
    }

    //gender update
    fun updateGender(newGender : String) {
        if (newGender != _gender.value) {
            _gender.update{newGender}
            _isEnabled.update{true}
        }
    }

    //gender update
    fun saveGender(){
        viewModelScope.launch(Dispatchers.IO){
            val sex = if (gender.value == "male") true else false
            val request = SexRequestDto(sex)
            repository.updateSex(request)
        }
        resetEnabled()
    }

    //profile update
    fun updateProfile(newProfile : Uri) {
        _profile.update { newProfile.toString() }
    }

    //profile update
    fun saveProfile() {
        viewModelScope.launch(Dispatchers.IO){
            repository.postProfilePhoto(profile.value)
        }
        resetEnabled()
    }

    //age update
    fun updateBirth(newBirth : Int) {
        _birth.update {newBirth}
    }

    //age update
    fun saveBirth(){
        viewModelScope.launch(Dispatchers.IO) {
            val today = Calendar.getInstance().weekYear
            val age = today - birth.value + 1
            val request = AgeRequestDto(age)
            repository.updateAge(request)
        }
        resetEnabled()
    }

    //버튼 활성화 여부 초기화
    private fun resetEnabled() {
        _isEnabled.update{false}
        _isDuplicated.update{false}
    }
}

sealed interface UserInfoUiState {
    data object Loading : UserInfoUiState

    data class UserInfo(
        val nickname : String,
        val profile : String,
        val gender : String,
        val birth : Int,
        val provider : String
    ) : UserInfoUiState
}