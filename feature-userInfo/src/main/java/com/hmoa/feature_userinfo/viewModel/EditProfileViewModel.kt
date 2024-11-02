package com.hmoa.feature_userinfo.viewModel

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.absolutePath
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.request.NickNameRequestDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val memberRepository: MemberRepository
) : ViewModel() {

    private var expiredTokenErrorState = MutableStateFlow<Boolean>(false)
    private var wrongTypeTokenErrorState = MutableStateFlow<Boolean>(false)
    private var unLoginedErrorState = MutableStateFlow<Boolean>(false)
    private var generalErrorState = MutableStateFlow<Pair<Boolean, String?>>(Pair(false, null))
    val errorUiState: StateFlow<ErrorUiState> = combine(
        expiredTokenErrorState,
        wrongTypeTokenErrorState,
        unLoginedErrorState,
        generalErrorState
    ) { expiredTokenError, wrongTypeTokenError, unknownError, generalError ->
        ErrorUiState.ErrorData(
            expiredTokenError = expiredTokenError,
            wrongTypeTokenError = wrongTypeTokenError,
            unknownError = unknownError,
            generalError = generalError
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ErrorUiState.Loading
    )
    val uiState: StateFlow<EditProfileUiState> = errorUiState.map {
        if (it is ErrorUiState.ErrorData && it.isValidate()) throw Exception("")
        val result = memberRepository.getMember()
        if (result.errorMessage != null) { throw Exception(result.errorMessage!!.message) }
        result.data!!
    }.asResult().map { result ->
        when (result) {
            Result.Loading -> EditProfileUiState.Loading
            is Result.Success -> {
                val data = result.data
                EditProfileUiState.Success(
                    profileImg = data.memberImageUrl,
                    nickname = MutableStateFlow(data.nickname)
                )
            }
            is Result.Error -> {
                if (result.exception.message != ""){
                    handleErrorType(
                        error = result.exception,
                        onExpiredTokenError = {expiredTokenErrorState.update{true}},
                        onWrongTypeTokenError = {wrongTypeTokenErrorState.update{true}},
                        onUnknownError = {unLoginedErrorState.update{true}},
                        onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}},
                    )
                }
                EditProfileUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = EditProfileUiState.Loading
    )

    //nickname 중복 검사
    fun checkNicknameDup(nick: String) {
        val requestDto = NickNameRequestDto(nick)
        viewModelScope.launch {
            val result = memberRepository.postExistsNickname(requestDto)
            if (result.errorMessage != null) {
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
                return@launch
            }
            if (uiState.value is EditProfileUiState.Success){
                (uiState.value as EditProfileUiState.Success).updateInfo(nick, !result.data!!)
            }
        }
    }

    //remote 정보 update
    fun saveInfo(
        nickname: String,
        profileImg: String?,
        context: Context,
        onSuccess: () -> Unit,
    ) {
        val path = absolutePath(context, profileImg?.toUri() ?: return) ?: return
        val file = File(path)
        val requestDto = NickNameRequestDto(nickname)
        viewModelScope.launch {
            val resultProfile = memberRepository.postProfilePhoto(file)
            val resultNickname = memberRepository.updateNickname(requestDto)

            if (resultProfile.errorMessage != null) {
                when(resultProfile.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                }
                return@launch
            } else if (resultNickname.errorMessage != null) {
                when(resultNickname.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                }
                return@launch
            }
        }
        onSuccess()
    }
}

sealed interface EditProfileUiState {
    data object Loading : EditProfileUiState
    data class Success(
        val profileImg: String?,
        var nickname: MutableStateFlow<String>,
        var isDuplicated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    ) : EditProfileUiState {
        fun updateInfo(newNickname: String, isDup: Boolean){
            this.isDuplicated.update{isDup}
            this.nickname.update { newNickname }
        }
    }
    data object Error: EditProfileUiState
}