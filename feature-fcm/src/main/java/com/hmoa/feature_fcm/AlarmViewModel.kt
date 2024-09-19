package com.hmoa.feature_fcm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.FcmRepository
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.response.AlarmResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val fcmRepository: FcmRepository
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

    val uiState : StateFlow<AlarmUiState> = flow{
        val result = fcmRepository.getFcmList()
        if (result.errorMessage is ErrorMessage) throw Exception(result.errorMessage!!.message)
        emit(result)
    }.asResult().map{ result ->
        when(result){
            is Result.Error -> {
                generalErrorState.update{Pair(true, result.exception.message)}
                AlarmUiState.Error
            }
            Result.Loading -> AlarmUiState.Loading
            is Result.Success -> AlarmUiState.Success(result.data.data!!.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = AlarmUiState.Loading
    )

    //읽음 처리 함수
    fun checkAlarm(id : Int){
        viewModelScope.launch{
            val result = fcmRepository.checkAlarm(id)
            if (result.errorMessage is ErrorMessage){
                generalErrorState.update{ Pair(true, result.errorMessage?.message)}
            }
            (uiState.value as AlarmUiState.Success).checkAlarm(id)
        }
    }

}

sealed interface AlarmUiState{
    data object Error : AlarmUiState
    data object Loading : AlarmUiState
    data class Success(val alarms : List<AlarmResponse>) : AlarmUiState {
        fun checkAlarm(id : Int){
            alarms.forEach{if (it.id == id) it.read = true}
        }
    }
}