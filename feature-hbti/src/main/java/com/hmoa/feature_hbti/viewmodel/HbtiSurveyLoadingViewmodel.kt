package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HbtiSurveyLoadingViewmodel @Inject constructor(
    private val memberRepository: MemberRepository,
) : ViewModel() {
    private var _userNameState = MutableStateFlow<String>("")
    val userNameState: StateFlow<String> = _userNameState
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserName()
        }
    }

    suspend fun getUserName() {
        flow { emit(memberRepository.getMember()) }.asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.data?.nickname != null) {
                        _userNameState.update { result.data.data?.nickname!! }
                    }
                }

                is Result.Error -> when (result.exception.message) {
                    ErrorMessageType.EXPIRED_TOKEN.message -> {
                        expiredTokenErrorState.update { true }
                    }

                    ErrorMessageType.WRONG_TYPE_TOKEN.message -> {
                        wrongTypeTokenErrorState.update { true }
                    }

                    ErrorMessageType.UNKNOWN_ERROR.message -> {
                        unLoginedErrorState.update { true }
                    }

                    else -> {
                        generalErrorState.update { Pair(true, result.exception.message) }
                    }
                }

                Result.Loading -> HbtiSurveyResultUiState.Loading
            }
        }
    }
}