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
import com.hmoa.core_model.response.OrderRecordDto
import com.hmoa.feature_userinfo.OrderPagingSource
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
class OrderRecordViewModel @Inject constructor(
    private val memberRepository: MemberRepository
): ViewModel() {

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

    val uiState: StateFlow<OrderRecordUiState> = errorUiState.map{
        orderRecordPagingSource()
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> OrderRecordUiState.Loading
            is Result.Success -> OrderRecordUiState.Success(result.data)
            is Result.Error -> {
                handleErrorType(
                    error = result.exception,
                    onExpiredTokenError = {expiredTokenErrorState.update{true}},
                    onWrongTypeTokenError = {wrongTypeTokenErrorState.update{true}},
                    onUnknownError = {unLoginedErrorState.update{true}},
                    onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}}
                )
                OrderRecordUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = OrderRecordUiState.Loading
    )


    //주문 내역 Paging
    private fun orderRecordPagingSource() : Flow<PagingData<OrderRecordDto>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = {
            getFavoriteCommentPaging()
        }
    ).flow.cachedIn(viewModelScope)


    //paging
    private fun getFavoriteCommentPaging() = OrderPagingSource(
        memberRepository = memberRepository
    )
}

sealed interface OrderRecordUiState{
    data object Loading: OrderRecordUiState
    data object Error: OrderRecordUiState
    data class Success(
        val orderRecords: Flow<PagingData<OrderRecordDto>>
    ): OrderRecordUiState
}