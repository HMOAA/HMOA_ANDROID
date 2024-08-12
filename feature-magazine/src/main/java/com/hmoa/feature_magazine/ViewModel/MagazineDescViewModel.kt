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
import com.hmoa.core_model.data.ErrorMessage
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
    private val loginRepository: LoginRepository
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
        val result = magazineRepository.getMagazine(id)
        if(result.errorMessage is ErrorMessage) throw Exception(result.errorMessage!!.message)
        result.data!!
    }.asResult().map{ result ->
        when(result) {
            Result.Loading -> MagazineDescUiState.Loading
            is Result.Success -> {
                val data = result.data

                //content 데이터 정리
                val contents = mutableListOf<MagazineContentItem>()
                for(x in 0 until data.contents.size){
                    contents.add(MagazineContentItem())
                }
                var curIdx = 0
                data.contents.forEachIndexed{ idx, value ->
                    if(value.type == "content"){
                        contents[curIdx].content = value.data
                    } else if(value.type == "header") {
                        contents[curIdx].header = value.data
                    } else if(value.type == "image"){
                        contents[curIdx].image = value.data
                    }
                    if(contents[curIdx].content != null && contents[curIdx].header != null){
                        curIdx += 1
                    }
                }

                //날짜 데이터 형식 변경
                val month = when(data.createAt.split(" ")[0]){
                    "Jan" -> 1
                    "Feb" -> 2
                    "Mar" -> 3
                    "Apr" -> 4
                    "May" -> 5
                    "Jun" -> 6
                    "Jul" -> 7
                    "Aug" -> 8
                    "Sep" -> 9
                    "Oct" -> 10
                    "Nov" -> 11
                    "Dec" -> 12
                    else -> 0
                }
                val day = data.createAt.split(" ")[1].replace(",", "")
                val year = data.createAt.split(" ")[2]
                val newDate = "${year}.${month}.${day}"

                //preview 개행 제거
                val preview = data.preview.replace("\\n", "")

                _isLiked.update{ data.liked }

                MagazineDescUiState.Success(
                    title = data.title,
                    createAt = newDate,
                    previewImgUrl = data.previewImgUrl,
                    contents = contents,
                    preview = preview,
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

data class MagazineContentItem(
    var header : String? = null,
    var content : String? = null,
    var image : String? = null
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