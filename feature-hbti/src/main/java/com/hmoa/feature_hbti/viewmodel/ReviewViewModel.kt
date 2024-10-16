package com.hmoa.feature_hbti.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.HShopReviewRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_model.response.ReviewResponseDto
import com.hmoa.feature_hbti.paging.ReviewPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
class ReviewViewModel @Inject constructor(
    private val hShopReviewRepository: HShopReviewRepository,
    private val reportRepository: ReportRepository
): ViewModel(){

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

    val uiState: StateFlow<ReviewUiState> = combine(flag, errorUiState){flag, errState ->
        if (errState is ErrorUiState.ErrorData && errState.isValidate()) throw Exception("")
        hShopReviewRepository.getMyOrders()
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> ReviewUiState.Loading
            is Result.Success -> {
                ReviewUiState.Success(
                    reviews = reviewPagingSource(),
                    myOrderIds = result.data.data!!.map{it.orderId},
                    myOrderInfos = result.data.data!!.map{"후기 작성하기 (${it.orderInfo})"}
                )
            }
            is Result.Error -> {
                if (result.exception.message != ""){
                    handleErrorType(
                        error = result.exception,
                        onExpiredTokenError = {expiredTokenErrorState.update{true}},
                        onWrongTypeTokenError = {wrongTypeTokenErrorState.update{true}},
                        onUnknownError = {unLoginedErrorState.update{true}},
                        onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}}
                    )
                }
                ReviewUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReviewUiState.Loading
    )

    private fun reviewPagingSource(): Flow<PagingData<ReviewResponseDto>> = Pager(
        config = PagingConfig(pageSize = 8), //page size 8
        pagingSourceFactory = {getReviewPaging()}
    ).flow.cachedIn(viewModelScope)

    private fun getReviewPaging() = ReviewPagingSource(
        hShopReviewRepository = hShopReviewRepository,
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
    fun handleNoDateError() = generalErrorState.update{Pair(true, "주문 후 이용 가능한 서비스 입니다.")}

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

sealed interface ReviewUiState{
    data object Loading: ReviewUiState
    data object Error: ReviewUiState
    data class Success(
        val reviews: Flow<PagingData<ReviewResponseDto>>,
        val myOrderIds: List<Int>,
        val myOrderInfos: List<String>
    ): ReviewUiState
}