package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.*
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_model.response.OrderDescriptionResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HbtiProcessViewmodel @Inject constructor(private val hbtiRepository: HshopRepository) : ViewModel() {
    private var _titlesState = MutableStateFlow<List<String>>(emptyList())
    private var _contentsState = MutableStateFlow<List<String>>(emptyList())
    private var _imgUrlState = MutableStateFlow<String>("")
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
    val uiState: StateFlow<HbtiProcessUiState> =
        combine(_titlesState, _contentsState, _imgUrlState) { titles, contents, imgUrl ->
            HbtiProcessUiState.Success(
                titles = titles,
                contents = contents,
                descriptionUrl = imgUrl
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HbtiProcessUiState.Loading
        )

    init {
        getOrderProcessDescription()
    }

    fun getOrderProcessDescription() {
        viewModelScope.launch {
            flow {
                val response = hbtiRepository.getOrderDescriptions()
                response.emitOrThrow { emit(it) }
            }.asResult().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        updateTitlesAndContents(result.data.data)
                        _imgUrlState.update { result.data.data?.orderDescriptionImgUrl ?: "" }
                    }

                    is Result.Error -> {
                        handleErrorType(
                            error = result.exception,
                            onExpiredTokenError = { expiredTokenErrorState.update { true } },
                            onUnknownError = { unLoginedErrorState.update { true } },
                            onWrongTypeTokenError = { wrongTypeTokenErrorState.update { true } },
                            onGeneralError = { generalErrorState.update { Pair(true, result.exception.message) } }
                        )
                    }

                    Result.Loading -> {}
                }
            }
        }
    }

    fun updateTitlesAndContents(result: OrderDescriptionResponseDto?) {
        val titles = mutableListOf<String>()
        val contents = mutableListOf<String>()
        result?.orderDescriptions?.map {
            titles.add(it.title)
            contents.add(it.content)
        }
        _titlesState.update { titles }
        _contentsState.update { contents }
    }
}

sealed interface HbtiProcessUiState {
    data object Error : HbtiProcessUiState
    data object Loading : HbtiProcessUiState
    data class Success(
        val titles: List<String>,
        val contents: List<String>,
        val descriptionUrl: String,
    ) : HbtiProcessUiState
}
