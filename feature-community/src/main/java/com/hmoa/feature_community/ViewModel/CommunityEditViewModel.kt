package com.hmoa.feature_community.ViewModel

import android.app.Application
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
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
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class CommunityEditViewModel @Inject constructor(
    private val application: Application,
    private val repository: CommunityRepository,
) : ViewModel() {
    private val context = application.applicationContext

    //가져올 게시글 id
    private val _id = MutableStateFlow<Int?>(null)
    val id get() = _id.asStateFlow()

    //title
    private val _title = MutableStateFlow("")
    val title get() = _title.asStateFlow()

    //content
    private val _content = MutableStateFlow("")
    val content get() = _content.asStateFlow()

    //category
    private val _category = MutableStateFlow<Category?>(null)
    val category get() = _category.asStateFlow()

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

    val uiState: StateFlow<CommunityEditUiState> = id.map { communityId ->
        if (communityId == null) throw NullPointerException("Id is NULL")
        val result = repository.getCommunity(communityId)
        if (result.errorMessage != null) {
            throw Exception(result.errorMessage!!.message)
        }
        result.data!!
    }.asResult()
        .map { result ->
            when (result) {
                Result.Loading -> CommunityEditUiState.Loading
                is Result.Success -> {
                    val data = result.data
                    _title.update { data.title }
                    _content.update { data.content }
                    _category.update {
                        val category = when (data.category) {
                            "시향기" -> Category.시향기
                            "추천" -> Category.추천
                            "자유" -> Category.자유
                            else -> throw IllegalArgumentException("올바르지 않은 Category")
                        }
                        category
                    }
                    _pictures.update { data.communityPhotos }
                    _newPictures.update { data.communityPhotos.map { it.photoUrl.toUri() } }
                    CommunityEditUiState.Success
                }
                is Result.Error -> {
                    CommunityEditUiState.Error
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3_000),
            initialValue = CommunityEditUiState.Loading
        )

    //id setting
    fun setId(id: Int?) = _id.update { id }
    //title update
    fun updateTitle(title: String) = _title.update { title }
    //content update
    fun updateContent(content: String) = _content.update { content }
    //사진 추가
    fun updatePictures(newPictures: List<Uri>) = _newPictures.update { it.plus(newPictures) }
    //사진 삭제
    fun deletePicture(uri: Uri) {
        _newPictures.update { it.minus(uri) }
        delPicture.add(uri)
    }
    //게시글 수정 POST
    fun updateCommunity() {
        viewModelScope.launch {
            val pictureUris = _pictures.value.map { it.photoUrl.toUri() }
            val addPictures = mutableListOf<Uri>()
            _newPictures.value.forEach {
                if (it !in pictureUris) {
                    addPictures.add(it)
                }
            }
            val images = addPictures.map {
                val uri = absolutePath(it) ?: throw NullPointerException("파일 경로가 NULL 입니다.")
                File(uri)
            }
            val delPictureId = getDeletePictureId(delPicture)
            if (id.value != null) {
                val result = repository.postCommunityUpdate(
                    images = images.toTypedArray(),
                    title = title.value,
                    content = content.value,
                    communityId = id.value!!,
                    deleteCommunityPhotoIds = delPictureId.toTypedArray()
                )
                if (result.errorMessage is ErrorMessage) {
                    generalErrorState.update { Pair(true, result.errorMessage!!.message) }
                }
            } else {
                generalErrorState.update { Pair(true,"id is null") }
            }
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
    private fun absolutePath(uri: Uri): String? {
        val contentResolver = context.contentResolver
        val filePath = (context.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
        val file = File(filePath)
        try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore: Exception) {
            return null
        }
        return file.absolutePath
    }
}

sealed interface CommunityEditUiState {
    data object Loading : CommunityEditUiState
    data object Success : CommunityEditUiState
    data object Error : CommunityEditUiState
}