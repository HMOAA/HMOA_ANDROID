package com.example.feature_userinfo.viewModel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hmoa.core_model.data.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val loginRepository: LoginRepository,
    private val getUserInfoUseCase: GetMyUserInfoUseCase
) : ViewModel() {

    //Login 여부
    private val _isLogin = MutableStateFlow(false)
    val isLogin get() = _isLogin.asStateFlow()

    private val errState = MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch {
            var authToken: String? = getAuthToken()
            if (authToken != null) {
                _isLogin.update { true }
            } else {
                _isLogin.update { false }
            }
        }
    }

    val uiState : StateFlow<UserInfoUiState> = errState.map{
        if (it != null){
            throw Exception(it)
        }
        val result = getUserInfoUseCase()
        if (result.exception is Exception){
            throw result.exception!!
        }
        result.data!!
    }.asResult().map{result ->
        when(result) {
            Result.Loading -> UserInfoUiState.Loading
            is Result.Success -> {
                UserInfoUiState.User(result.data)
            }
            is Result.Error -> UserInfoUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = UserInfoUiState.Loading
    )

    fun getAuthToken(): String? {
        var result: String? = null
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.getAuthToken().onEmpty { }.collectLatest {
                result = it
            }
        }
        return result
    }

    //로그아웃
    fun logout(){
        /** 저장되어 있는 토큰 정보를 모두 날리고 LoginRoute로 Navigation 이게 맞음 */
    }

    //계정 삭제
    fun delAccount(){
        /** 저장되어 있는 토큰 정보를 모두 날리고 LoginRoute로 Navigation 이게 맞음 */
        viewModelScope.launch{
            try{
                memberRepository.deleteMember()
            } catch(e : Exception){
                errState.update{ e.message }
            }
        }
    }
}

sealed interface UserInfoUiState {
    data object Loading : UserInfoUiState

    data class User(
        val userInfo : UserInfo
    ) : UserInfoUiState

    data object Error : UserInfoUiState
}