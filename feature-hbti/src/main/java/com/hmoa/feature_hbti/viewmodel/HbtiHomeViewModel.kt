package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.HShopReviewRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_model.response.ReviewResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HbtiHomeViewModel @Inject constructor(
    private val hShopReviewRepository: HShopReviewRepository,
    private val reportRepository: ReportRepository,
): ViewModel() {

    private val flag = MutableStateFlow<Boolean>(false)
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

    val uiState = flag.map{
        val response = hShopReviewRepository.getReviews(0)
        if (response.errorMessage != null){
            throw Exception(response.errorMessage!!.message)
        }
        response.data
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> HbtiHomeUiState.Loading
            is Result.Success -> {
                val data = if(result.data!!.data.size > 5) result.data!!.data.subList(0,5) else result.data!!.data
                HbtiHomeUiState.Success(data)
            }
            is Result.Error -> {
                handleErrorType(
                    error = result.exception,
                    onExpiredTokenError = {expiredTokenErrorState.update{true}},
                    onUnknownError = {unLoginedErrorState.update{true}},
                    onWrongTypeTokenError = {wrongTypeTokenErrorState.update{true}},
                    onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}}
                )
                HbtiHomeUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HbtiHomeUiState.Loading
    )

    fun onHeartClick(reviewId: Int, isLiked: Boolean){
        viewModelScope.launch{
            val result = if (isLiked) hShopReviewRepository.deleteReviewLike(reviewId) else hShopReviewRepository.putReviewLike(reviewId)
            if(result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, result.errorMessage!!.message)}
                }
            }
        }
    }
    fun reportReview(reviewId: Int){
        viewModelScope.launch{
            val result = reportRepository.reportReview(reviewId)
            if (result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update{true}}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update{true}}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update{true}}
                    else -> {generalErrorState.update{Pair(true, result.errorMessage!!.message)}}
                }
            }
        }
    }

    fun deleteReview(reviewId: Int){
        viewModelScope.launch{
            val result = hShopReviewRepository.deleteReview(reviewId)
            if(result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update{true}}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update{true}}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update{true}}
                    else -> {generalErrorState.update{Pair(true, result.errorMessage!!.message)}}
                }
                return@launch
            }
            flag.update{!flag.value}
        }
    }
}

sealed interface HbtiHomeUiState{
    data object Error: HbtiHomeUiState
    data object Loading: HbtiHomeUiState
    data class Success(
        val reviews: List<ReviewResponseDto>
    ): HbtiHomeUiState
}