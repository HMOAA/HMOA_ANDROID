package com.hmoa.feature_home.viewmodel

import ResultResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.entity.data.AllPerfumeScreenId
import com.hmoa.core_domain.repository.MainRepository
import com.hmoa.core_model.response.HomeMenuAllResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPerfumeViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    var _uiState = MutableStateFlow<AllPerfumeUiState>(AllPerfumeUiState.Loading)
    val uiState: StateFlow<AllPerfumeUiState> = _uiState
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

    fun getPerfumeByScreenId(screenId: AllPerfumeScreenId?) {
        viewModelScope.launch(Dispatchers.IO) {
            var perfumes = emptyFlow<ResultResponse<List<HomeMenuAllResponseDto>>>()
            if (screenId != null) {
                perfumes = flow {
                    when (screenId) {
                        AllPerfumeScreenId.First -> emit(mainRepository.getFirstMenu())
                        AllPerfumeScreenId.Second -> emit(mainRepository.getSecondMenu())
                        AllPerfumeScreenId.Third -> emit(mainRepository.getThirdMenu())
                    }
                }
            }

            perfumes.asResult().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update { AllPerfumeUiState.Data(perfumeList = result.data.data?.toImmutableList()) }
                    }

                    is Result.Error -> {
                        when (result.exception.message) {
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
                        AllPerfumeUiState.Error
                    }

                    is Result.Loading -> {
                        AllPerfumeUiState.Loading
                    }
                }
            }

        }
    }

    sealed interface AllPerfumeUiState {
        data object Loading : AllPerfumeUiState
        data class Data(
            val perfumeList: ImmutableList<HomeMenuAllResponseDto>?,
        ) : AllPerfumeUiState

        data object Error : AllPerfumeUiState
    }
}
