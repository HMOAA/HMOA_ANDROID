package com.hmoa.feature_magazine.ViewModel

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.feature_magazine.MagazinePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

const val PAGE_SIZE = 10

@HiltViewModel
class MagazineDescViewModel @Inject constructor(
    private val magazineRepository: MagazineRepository
) : ViewModel() {
    private val id = MutableStateFlow<Int?>(null)
    private val _isLiked = MutableStateFlow<Boolean?>(null)
    val isLiked get() = _isLiked.asStateFlow()

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

    val uiState : StateFlow<MagazineDescUiState> = id.map{
        if(it == null) throw NullPointerException("Magazine ID is NULL")
        val result = magazineRepository.getMagazine(it)
        if(result.errorMessage is ErrorMessage) throw Exception(result.errorMessage!!.message)
        result.data!!
    }.asResult().map{ result ->
        when(result) {
            Result.Loading -> MagazineDescUiState.Loading
            is Result.Success -> {
                val data = result.data
                val contents = mutableListOf<MagazineContentItem>()
                data.contents.forEach{
                    if(it.type == "header"){
                        contents.add(MagazineContentItem(header = it.data, content = ""))
                    } else {
                        contents[contents.lastIndex].content = it.data
                    }
                }
                MagazineDescUiState.Success(
                    title = data.title,
                    createAt = data.createAt,
                    previewImgUrl = data.previewImgUrl,
                    contents = contents,
                    preview = data.preview,
                    tags = data.tags,
                    viewCount = data.viewCount,
                    likeCount = data.likeCount
                )
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
}

data class MagazineContentItem(
    val header : String,
    var content : String
)

sealed interface MagazineDescUiState{
    data object Loading : MagazineDescUiState
    data class Success(
        val title : String,
        val createAt : String,
        val previewImgUrl : String,
        val preview : String,
        val contents : List<MagazineContentItem>,
        val tags : List<String>,
        val viewCount : Int,
        val likeCount : Int,
    ) : MagazineDescUiState
    data class Error(
        val message : String
    ) : MagazineDescUiState
}