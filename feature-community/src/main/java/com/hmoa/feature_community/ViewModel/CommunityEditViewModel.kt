package com.hmoa.feature_community.ViewModel

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityPhotoDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CommunityEditViewModel @Inject constructor(
    private val repository: CommunityRepository,
) : ViewModel() {

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

    //error state
    private val _errState = MutableStateFlow<String?>(null)
    val errState get() = _errState.asStateFlow()

    //loading state
    private val _loadingState = MutableStateFlow(false)

    //id setting
    fun setId(id: Int) {
        _id.update { id }

        //id 기반으로 정보를 가져옴
        getCommunityDescription(id)
    }

    //title update
    fun updateTitle(title: String) {
        _title.update { title }
    }

    //content update
    fun updateContent(content: String) {
        _content.update { content }
    }

    //사진 추가
    fun updatePictures(newPictures: List<Uri>) {
        _newPictures.update{
            it.plus(newPictures)
        }
    }

    //사진 삭제
    fun deletePicture(uri : Uri){
        _newPictures.update{
            it.minus(uri)
        }
        delPicture.add(uri)
    }

    //게시글 수정 POST
    fun updateCommunity() {
        viewModelScope.launch {

            val images = urisToFiles(newPictures.value)
            val delPictureId = getDeletePictureId(delPicture)
            if (id.value != null) {
                repository.postCommunityUpdate(
                    images = images.toTypedArray(),
                    title = title.value,
                    content = content.value,
                    communityId = id.value!!,
                    deleteCommunityPhotoIds = delPictureId.toTypedArray()
                )
            } else {
                _errState.update { "id is null" }
            }
        }
    }

    //id 기반 데이터 받아오기
    private fun getCommunityDescription(id: Int) {
        viewModelScope.launch {
            flow {
                val result = repository.getCommunity(id)
                if (result.exception is Exception) {
                    throw result.exception!!
                } else {
                    emit(result.data!!)
                }
            }.asResult()
                .map { result ->
                    when (result) {
                        is Result.Loading -> {
                            _loadingState.update{ false }
                        }
                        is Result.Error -> {
                            _errState.update{ result.exception.toString() }
                        }
                        is Result.Success -> {
                            val data = result.data
                            _title.update { data.title }
                            _content.update { data.content }
                            _category.update {
                                val category = when (it?.name) {
                                    "시향기" -> Category.시향기
                                    "추천" -> Category.추천
                                    "자유" -> Category.자유
                                    else -> {
                                        //불가능
                                        throw IllegalArgumentException("올바르지 않은 Category")
                                    }
                                }
                                category
                            }
                            _pictures.update { data.communityPhotos }
                            _newPictures.update{ data.communityPhotos.map{it.photoUrl.toUri()} }
                            _loadingState.update{ true }
                        }
                    }
                }
            }
        }

    //삭제할 사진 id 계산
    private fun getDeletePictureId(pictures : List<Uri>) : ArrayList<Int> {
        val ids = arrayListOf<Int>()
        val defaultUris = _pictures.value.map{it.photoUrl.toUri()}
        pictures.forEach{ picture ->
            if (picture in defaultUris) {
                ids.add(_pictures.value[defaultUris.indexOf(picture)].photoId)
            }
        }
        return ids
    }

    //uri >> file 변환
    private fun urisToFiles(uris : List<Uri>) : ArrayList<File> {
        val images = arrayListOf<File>()
        uris.map{ picture ->
            val path = picture.path ?: return@map
            Log.d("TAG TEST", "path : ${path}")
            images.add(File(path))
        }
        return images
    }
}