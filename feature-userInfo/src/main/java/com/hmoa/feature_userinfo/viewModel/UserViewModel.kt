package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetAuthAndRememberedTokenUseCase
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hmoa.core_domain.usecase.GetNicknameUseCase
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val _birth = MutableStateFlow(2000)
    val birth : StateFlow<Int> get() = _birth

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
                            _birth.update {result.data.age}
                            _gender.update {result.data.sex}
                            _profile.update {result.data.memberImageUrl}
                            _nickname.update {result.data.nickname}
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
        UserInfoUiState::UserInfo
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = UserInfoUiState.Loading
    )
}

sealed interface UserInfoUiState {
    data object Loading : UserInfoUiState

    data class UserInfo(
        val nickname : String,
        val profile : String,
        val gender : String,
        val birth : Int
    ) : UserInfoUiState

    data object Empty : UserInfoUiState
}