package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.*
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
        flow {
            val result = memberRepository.getMember()
            result.emitOrThrow { emit(it) }
        }.asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.data?.nickname != null) {
                        _userNameState.update { result.data.data?.nickname!! }
                    }
                }

                is Result.Error -> {
                    handleErrorType(
                        error = result.exception,
                        onExpiredTokenError = { expiredTokenErrorState.update { true } },
                        onWrongTypeTokenError = { wrongTypeTokenErrorState.update { true } },
                        onUnknownError = { unLoginedErrorState.update { true } },
                        onGeneralError = { generalErrorState.update { Pair(true, result.exception.message) } }
                    )
                }

                Result.Loading -> HbtiSurveyResultUiState.Loading
            }
        }
    }
}