package com.hmoa.feature_userinfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.GetRefundRecordResponseDto
import com.hmoa.feature_userinfo.paging.RefundRecordPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RefundRecordViewModel @Inject constructor(
    private val memberRepository: MemberRepository
): ViewModel(){

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

    val uiState: StateFlow<RefundRecordUiState> = errorUiState.map{
        refundRecordPagingSource()
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> RefundRecordUiState.Loading
            is Result.Success -> RefundRecordUiState.Success(result.data)
            is Result.Error -> {
                handleErrorType(
                    error = result.exception,
                    onExpiredTokenError = {expiredTokenErrorState.update{true}},
                    onWrongTypeTokenError = {wrongTypeTokenErrorState.update{true}},
                    onUnknownError = {unLoginedErrorState.update{true}},
                    onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}}
                )
                RefundRecordUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RefundRecordUiState.Loading
    )


    //주문 내역 Paging
    private fun refundRecordPagingSource() : Flow<PagingData<GetRefundRecordResponseDto>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = {
            getRefundRecordPaging()
        }
    ).flow.cachedIn(viewModelScope)


    //paging
    private fun getRefundRecordPaging() = RefundRecordPagingSource(
        memberRepository = memberRepository
    )
}

sealed interface RefundRecordUiState{
    data object Loading: RefundRecordUiState
    data object Error: RefundRecordUiState
    data class Success(
        val data: Flow<PagingData<GetRefundRecordResponseDto>>
    ): RefundRecordUiState
}