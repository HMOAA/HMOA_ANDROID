package com.hmoa.feature_magazine.ViewModel

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
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_domain.usecase.GetMagazineDescription
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_domain.entity.data.MagazineSuccessItem
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.feature_magazine.MagazinePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MagazineDescViewModel @Inject constructor(
    private val magazineRepository: MagazineRepository,
    private val loginRepository: LoginRepository,
    val getMagazineDescription : GetMagazineDescription
) : ViewModel() {
    private val PAGE_SIZE = 5
    private val id = MutableStateFlow<Int?>(null)
    private val _isLiked = MutableStateFlow<Boolean?>(null)
    val isLiked get() = _isLiked.asStateFlow()
    private val authToken = MutableStateFlow<String?>(null)

    private val flag = MutableStateFlow<Boolean?>(false)

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
    init{getAuthToken()}

    val uiState : StateFlow<MagazineDescUiState> = combine(id, flag){ id, flag ->
        if(id == null) throw NullPointerException("Magazine ID is NULL")
        if(flag == null) throw Exception(ErrorMessageType.UNKNOWN_ERROR.message)
        val result = getMagazineDescription(id)
        if(result.errorMessage is ErrorMessage) throw Exception(result.errorMessage!!.message)
        result.data!!
    }.asResult().map{ result ->
        when(result) {
            Result.Loading -> MagazineDescUiState.Loading
            is Result.Success -> {
                val data = result.data
                _isLiked.update{ data.isLiked }
                MagazineDescUiState.Success(magazine = data)
            }
            is Result.Error -> {
                generalErrorState.update{ Pair(true, result.exception.message) }
                MagazineDescUiState.Error(result.exception.message ?: "Error Message is NULL")
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = MagazineDescUiState.Loading
    )

    //id 설정
    fun setId(magazineId : Int?){
        id.update{ magazineId }
    }

    fun magazinePagingSource(): Flow<PagingData<MagazineSummaryResponseDto>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getMagazinePaging()
        }
    ).flow.cachedIn(viewModelScope)

    private fun getMagazinePaging() = MagazinePagingSource(
        magazineRepository = magazineRepository,
    )
    private fun getAuthToken(){
        viewModelScope.launch{
            loginRepository.getAuthToken().onEmpty{ }.collectLatest {
                authToken.value = it
            }
        }
    }
    //매거진 좋아요 갱신
    fun updateMagazineLike() {
        viewModelScope.launch{
            if(authToken.value == null) {
                unLoginedErrorState.update{ true }
                flag.update{ null }
                return@launch
            }
            if (isLiked.value == null) {
                generalErrorState.update{ Pair(true, "정보를 가져오지 못했습니다.")}
                flag.update{ null }
                return@launch
            }
            val result = if(isLiked.value!!) magazineRepository.deleteMagazineHeart(id.value!!)
                else magazineRepository.putMagazineHeart(id.value!!)
            if (result.errorMessage is ErrorMessage){
                generalErrorState.update{ Pair(true, result.errorMessage!!.message) }
                flag.update{ null }
                return@launch
            }
            _isLiked.update{ !isLiked.value!! }
            flag.update{ !flag.value!! }
        }
    }
}


sealed interface MagazineDescUiState{
    data object Loading : MagazineDescUiState
    data class Success(
        val magazine : MagazineSuccessItem
    ) : MagazineDescUiState
    data class Error(
        val message : String
    ) : MagazineDescUiState
}