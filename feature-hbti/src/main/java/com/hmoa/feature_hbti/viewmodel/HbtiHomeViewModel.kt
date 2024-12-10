package com.hmoa.feature_hbti.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.*
import com.hmoa.core_domain.repository.HShopReviewRepository
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.response.HbtiHomeMetaDataResponse
import com.hmoa.core_model.response.ReviewResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HbtiHomeViewModel @Inject constructor(
    private val hShopReviewRepository: HShopReviewRepository,
    private val reportRepository: ReportRepository,
    private val surveyRepository: SurveyRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val flag = MutableStateFlow<Boolean>(false)
    private val reviewsState = MutableStateFlow<List<ReviewResponseDto>>(listOf())
    private val metadataState = MutableStateFlow<HbtiHomeMetaDataResponse?>(null)
    private val _isOrderedWarningNeedState = MutableStateFlow<Boolean>(false)
    val isOrderedWarningNeedState: StateFlow<Boolean> = _isOrderedWarningNeedState
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

    val uiState: StateFlow<HbtiHomeUiState> =
        combine(reviewsState, metadataState) { _reviews, _metadata ->
            HbtiHomeUiState.Success(
                reviews = _reviews,
                metadata = _metadata,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HbtiHomeUiState.Loading
        )

    init {
        getMetaData()
        getReviews()
        checkIsLogined()
    }

    fun checkIsLogined() {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.getAuthToken().collectLatest {
                if (it == null) {
                    unLoginedErrorState.update { true }
                }
            }
        }
    }

    fun getMetaData() {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                val response = surveyRepository.getHbtiHomeMetaDataResult()
                response.emitOrThrow { emit(it) }
            }.asResult().collectLatest { result ->
                when (result) {
                    Result.Loading -> HbtiHomeUiState.Loading
                    is Result.Success -> {
                        Log.d("HbtiHomeViewMdoel", "data:${result.data.data}")
                        metadataState.update { result.data.data }
                    }

                    is Result.Error -> {
                        handleErrorType(
                            error = result.exception,
                            onExpiredTokenError = { expiredTokenErrorState.update { true } },
                            onUnknownError = { unLoginedErrorState.update { true } },
                            onWrongTypeTokenError = { wrongTypeTokenErrorState.update { true } },
                            onGeneralError = { generalErrorState.update { Pair(true, result.exception.message) } }
                        )
                        HbtiHomeUiState.Error
                    }
                }
            }
        }
    }

    fun getReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                val response = hShopReviewRepository.getReviews(0)
                response.emitOrThrow { emit(it) }
            }.asResult().collectLatest { result ->
                when (result) {
                    Result.Loading -> HbtiHomeUiState.Loading
                    is Result.Success -> {
                        val data = result.data.data?.data
                        if (data != null) {
                            if (data.size > 5) data.subList(0, 5) else data
                            reviewsState.update { data }
                        }
                    }

                    is Result.Error -> {
                        handleErrorType(
                            error = result.exception,
                            onExpiredTokenError = { expiredTokenErrorState.update { true } },
                            onUnknownError = { unLoginedErrorState.update { true } },
                            onWrongTypeTokenError = { wrongTypeTokenErrorState.update { true } },
                            onGeneralError = { generalErrorState.update { Pair(true, result.exception.message) } }
                        )
                        HbtiHomeUiState.Error
                    }
                }
            }
        }
    }

    fun onHeartClick(reviewId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            val result =
                if (isLiked) hShopReviewRepository.deleteReviewLike(reviewId) else hShopReviewRepository.putReviewLike(
                    reviewId
                )
            if (result.errorMessage != null) {
                when (result.errorMessage!!.message) {
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update { true }
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update { true }
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update { true }
                    else -> generalErrorState.update { Pair(true, result.errorMessage!!.message) }
                }
            }
        }
    }

    fun onAfterOrderClick(onAvailable: () -> Unit) {
        Log.d("HbtiHomeViewModel", "isOrderedWarningNeedState.value: ${isOrderedWarningNeedState.value}")
        if (metadataState.value?.isOrdered ?: true) {
            onAvailable()
        } else {
            _isOrderedWarningNeedState.update { true }
        }
    }

    fun initializeIsOrderWarningNeedState() {
        _isOrderedWarningNeedState.update { false }
        Log.d("HbtiHomeViewModel", "isOrderedWarningNeedState.value: ${isOrderedWarningNeedState.value}")
    }

    fun reportReview(reviewId: Int) {
        viewModelScope.launch {
            val result = reportRepository.reportReview(reviewId)
            if (result.errorMessage != null) {
                when (result.errorMessage!!.message) {
                    ErrorMessageType.UNKNOWN_ERROR.name -> {
                        unLoginedErrorState.update { true }
                    }

                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {
                        wrongTypeTokenErrorState.update { true }
                    }

                    ErrorMessageType.EXPIRED_TOKEN.name -> {
                        expiredTokenErrorState.update { true }
                    }

                    else -> {
                        generalErrorState.update { Pair(true, result.errorMessage!!.message) }
                    }
                }
            }
        }
    }

    fun deleteReview(reviewId: Int) {
        viewModelScope.launch {
            val result = hShopReviewRepository.deleteReview(reviewId)
            if (result.errorMessage != null) {
                when (result.errorMessage!!.message) {
                    ErrorMessageType.UNKNOWN_ERROR.name -> {
                        unLoginedErrorState.update { true }
                    }

                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {
                        wrongTypeTokenErrorState.update { true }
                    }

                    ErrorMessageType.EXPIRED_TOKEN.name -> {
                        expiredTokenErrorState.update { true }
                    }

                    else -> {
                        generalErrorState.update { Pair(true, result.errorMessage!!.message) }
                    }
                }
                return@launch
            }
            flag.update { !flag.value }
        }
    }
}

sealed interface HbtiHomeUiState {
    data object Error : HbtiHomeUiState
    data object Loading : HbtiHomeUiState
    data class Success(
        val reviews: List<ReviewResponseDto>,
        val metadata: HbtiHomeMetaDataResponse?
    ) : HbtiHomeUiState
}