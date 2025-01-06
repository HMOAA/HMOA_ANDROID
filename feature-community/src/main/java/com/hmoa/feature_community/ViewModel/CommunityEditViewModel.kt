package com.hmoa.feature_community.ViewModel

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.absolutePath
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.Category
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.response.CommunityPhotoDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CommunityEditViewModel @Inject constructor(
    private val repository: CommunityRepository,
) : ViewModel() {
    //가져올 게시글 id
    private val id = MutableStateFlow<Int>(-1)

    //pictures
    private val _pictures = MutableStateFlow<List<CommunityPhotoDefaultResponseDto>>(listOf())

    //추가할 pictures
    private val _newPictures = MutableStateFlow<List<Uri>>(listOf())
    val newPictures get() = _newPictures.asStateFlow()

    private val delPicture = mutableListOf<Uri>()

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

    val uiState: StateFlow<CommunityEditUiState> = combine(id, errorUiState) { communityId, errorState ->
        if (errorState is ErrorUiState.ErrorData && errorState.isValidate()) throw Exception("")
        val result = repository.getCommunity(communityId!!)
        if (result.errorMessage is ErrorMessage) throw Exception(result.errorMessage!!.message)
        result.data!!
    }.asResult().map { result ->
            when (result) {
                Result.Loading -> CommunityEditUiState.Loading
                is Result.Success -> {
                    val category = when(result.data.category){
                        "시향기" -> Category.시향기
                        "추천" -> Category.추천
                        "자유" -> Category.자유
                        else -> throw IllegalArgumentException("올바르지 않은 Category")
                    }
                    _pictures.update { result.data.communityPhotos }
                    _newPictures.update { result.data.communityPhotos.map { it.photoUrl.toUri() } }
                    CommunityEditUiState.Success(
                        title = result.data.title,
                        content = result.data.content,
                        category = category
                    )
                }
                is Result.Error -> {
                    if (result.exception.message != "") {
                        handleErrorType(
                            error = result.exception,
                            onExpiredTokenError = { expiredTokenErrorState.update { true } },
                            onWrongTypeTokenError = { wrongTypeTokenErrorState.update { true } },
                            onUnknownError = { unLoginedErrorState.update { true } },
                            onGeneralError = {generalErrorState.update { Pair(true, result.exception.message) }}
                        )
                    }
                    CommunityEditUiState.Error
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3_000),
            initialValue = CommunityEditUiState.Loading
        )

    //id setting
    fun setId(newId: Int?) {
        if (newId == null) {
            generalErrorState.update { Pair(true, "해당 게시글을 찾을 수 없습니다.") }
            return
        }
        id.update {newId}
    }

    //사진 추가
    fun updatePictures(newPictures: List<Uri>) = _newPictures.update { it.plus(newPictures) }
    //사진 삭제
    fun deletePicture(uri: Uri) {
        _newPictures.update { it.minus(uri) }
        delPicture.add(uri)
    }

    //게시글 수정 POST
    fun updateCommunity(context: Context, title: String, content: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val pictureUris = _pictures.value.map { it.photoUrl.toUri() }
            val addPictures = mutableListOf<Uri>()
            _newPictures.value.forEach {
                if (it !in pictureUris) {
                    addPictures.add(it)
                }
            }
            val images = addPictures.map {
                val uri = absolutePath(context, it)
                if (uri == null) generalErrorState.update { Pair(true, "파일 경로가 NULL 입니다.") }
                File(uri!!)
            }
            val delPictureId = getDeletePictureId(delPicture)
            val response = repository.postCommunityUpdate(
                images = images.toTypedArray(),
                title = title,
                content = content,
                communityId = id.value,
                deleteCommunityPhotoIds = delPictureId.toTypedArray()
            )

            if (response.errorMessage != null) {
                when(response.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> unLoginedErrorState.update{true}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> wrongTypeTokenErrorState.update{true}
                    ErrorMessageType.EXPIRED_TOKEN.name -> expiredTokenErrorState.update{true}
                    else -> generalErrorState.update{Pair(true, response.errorMessage!!.message)}
                }
                return@launch
            }
            onSuccess()
        }
    }

    //삭제할 사진 id 계산
    private fun getDeletePictureId(pictures: List<Uri>): ArrayList<Int> {
        val ids = arrayListOf<Int>()
        val defaultUris = _pictures.value.map { it.photoUrl.toUri() }
        pictures.forEach { picture ->
            if (picture in defaultUris) {
                ids.add(_pictures.value[defaultUris.indexOf(picture)].photoId)
            }
        }
        return ids
    }
}

sealed interface CommunityEditUiState {
    data object Loading : CommunityEditUiState
    data class Success(
        val title: String,
        val content: String,
        val category: Category
    ) : CommunityEditUiState
    data object Error : CommunityEditUiState
}