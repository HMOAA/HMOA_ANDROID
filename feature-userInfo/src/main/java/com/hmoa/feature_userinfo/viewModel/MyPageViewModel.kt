package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val loginRepository: LoginRepository,
    private val getUserInfoUseCase: GetMyUserInfoUseCase
) : ViewModel() {
    private val authTokenState = MutableStateFlow<String?>(null)

    //Login 여부
    val isLogin = authTokenState.map{it != null}

    private val errState = MutableStateFlow<String?>(null)

    init {
        getAuthToken()
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
                val data = result.data
                UserInfoUiState.User(data.profile, data.nickname, data.provider)
            }
            is Result.Error -> UserInfoUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = UserInfoUiState.Loading
    )

    private fun getAuthToken() {
        viewModelScope.launch {
            loginRepository.getAuthToken().onEmpty { }.collectLatest {
                authTokenState.value = it
            }
        }
    }

    //로그아웃
    fun logout(){
        /** 저장되어 있는 토큰 정보를 모두 날리고 LoginRoute로 Navigation 이게 맞음 */
    }

    //계정 삭제
    fun delAccount(){
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
        val profile : String,
        val nickname : String,
        val provider : String,
    ) : UserInfoUiState
    data object Error : UserInfoUiState
}