package com.example.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.FcmRepository
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.usecase.GetMyUserInfoUseCase
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.request.FCMTokenSaveRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val loginRepository: LoginRepository,
    private val fcmRepository: FcmRepository,
    private val getUserInfoUseCase: GetMyUserInfoUseCase
) : ViewModel() {
    private var authTokenState = MutableStateFlow<String?>(null)

    //fcm token
    private val fcmToken = MutableStateFlow<String?>(null)
    private val _isEnabled = MutableStateFlow<Boolean>(false)
    val isEnabled get() = _isEnabled.asStateFlow().apply{
        viewModelScope.launch{
            this@apply.collectLatest{
                if(it){
                    FirebaseMessaging.getInstance().token.addOnSuccessListener{token ->
                        viewModelScope.launch{
                            val requestDto = FCMTokenSaveRequestDto(fcmtoken = token)
                            val result = fcmRepository.postRemoteFcmToken(requestDto)
                            fcmRepository.saveLocalFcmToken(token)
                            if (result.errorMessage is ErrorMessage) generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                        }
                    }
                } else {
                    val remoteResult = fcmRepository.deleteRemoteFcmToken()
                    fcmRepository.deleteLocalFcmToken()
                    if (remoteResult.errorMessage is ErrorMessage) generalErrorState.update{Pair(true, remoteResult.errorMessage!!.message)}
                    else {}
                }
                fcmRepository.saveNotificationEnabled(it)
            }
        }
    }


    //Login 여부
    val isLogin = authTokenState.map { it != null }
    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var memberNotFoundErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorUiState: StateFlow<ErrorUiState> = combine(
        expiredTokenErrorState,
        wrongTypeTokenErrorState,
        unLoginedErrorState,
        memberNotFoundErrorState,
        generalErrorState
    ) { expiredTokenError, wrongTypeTokenError, unknownError, memberNotFoundError, generalError ->
        ErrorUiState.ErrorData(
            expiredTokenError = expiredTokenError,
            wrongTypeTokenError = wrongTypeTokenError,
            unknownError = unknownError,
            memberNotFoundError = memberNotFoundError,
            generalError = generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )

    init {
        getAuthToken()
        getFcmToken()
        getFcmEnabled()
    }

    val uiState: StateFlow<UserInfoUiState> = flow {
        if (authTokenState.value == null) {throw Exception(ErrorMessageType.UNKNOWN_ERROR.message)}
        val result = getUserInfoUseCase()
        if (result.errorMessage != null) {throw Exception(result.errorMessage!!.message)}
        emit(result.data!!)
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> UserInfoUiState.Loading
            is Result.Success -> {
                val data = result.data
                UserInfoUiState.User(data.profile, data.nickname, data.provider)
            }

            is Result.Error -> {
                when (result.exception.message) {
                    ErrorMessageType.EXPIRED_TOKEN.message -> expiredTokenErrorState.update { true }
                    ErrorMessageType.WRONG_TYPE_TOKEN.message -> wrongTypeTokenErrorState.update { true }
                    ErrorMessageType.UNKNOWN_ERROR.message -> unLoginedErrorState.update { true }
                    ErrorMessageType.MEMBER_NOT_FOUND.message -> memberNotFoundErrorState.update { true }
                    else -> generalErrorState.update { Pair(true, result.exception.message) }
                }
                UserInfoUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = UserInfoUiState.Loading
    )

    private fun getAuthToken() {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.getAuthToken().onEmpty { }.collectLatest {
                authTokenState.value = it
            }
        }
    }
    private fun getFcmToken(){viewModelScope.launch{fcmToken.update{fcmRepository.getLocalFcmToken().onEmpty{ }.first()}}}
    private fun getFcmEnabled(){viewModelScope.launch{_isEnabled.update { fcmRepository.getNotificationEnabled().stateIn(viewModelScope).value }} }
    //알림 설정 변경
    fun changeAlarmSetting(isChecked: Boolean){
        viewModelScope.launch{
            _isEnabled.update{isChecked}
        }
    }
    //로그아웃
    suspend fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            fcmRepository.deleteRemoteFcmToken()
            fcmRepository.deleteLocalFcmToken()
            loginRepository.deleteAuthToken()
            loginRepository.deleteRememberedToken()
        }
    }

    //계정 삭제
    fun delAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            fcmRepository.deleteRemoteFcmToken()
            fcmRepository.deleteLocalFcmToken()
            try {
                memberRepository.deleteMember()
            } catch (e: Exception) {
                generalErrorState.update { Pair(true, "계정 삭제에 실패했습니다 :(") }
            }
            loginRepository.deleteAuthToken()
            loginRepository.deleteRememberedToken()
        }
    }
}

sealed interface UserInfoUiState {
    data object Loading : UserInfoUiState
    data class User(
        val profile: String,
        val nickname: String,
        val provider: String,
    ) : UserInfoUiState

    data object Error : UserInfoUiState
}