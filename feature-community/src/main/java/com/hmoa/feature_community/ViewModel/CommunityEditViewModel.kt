package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityPhotoDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CommunityEditViewModel @Inject constructor(
    private val repository : CommunityRepository,
) : ViewModel() {

    //가져올 게시글 id
    private val _id = MutableStateFlow<Int?>(null)
    val id get() = _id.asStateFlow()

    //loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

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
    private val _pictures = MutableStateFlow<List<CommunityPhotoDefaultResponseDto>>(emptyList())
    val pictures get() = _pictures.asStateFlow()

    //추가할 pictures
    private val _addPictures = MutableStateFlow<Array<File>>(arrayOf())
    val addPictures get() = _addPictures.asStateFlow()

    //삭제할 pictures
    private val _delPictures = MutableStateFlow<Array<Int>>(arrayOf())
    val delPictures get() = _delPictures.asStateFlow()

    //error state
    private val _errState = MutableStateFlow("")
    val errState get() = _errState.asStateFlow()

    //ui state
    val uiState : StateFlow<CommunityEditUiState> = combine(
        title,
        content,
        category,
        pictures,
        CommunityEditUiState::Community
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1_000),
        initialValue = CommunityEditUiState.Loading
    )

    //id 기반 데이터 받아오기
    private fun getCommunityDescription(id : Int){
        viewModelScope.launch{
            flow{
                val result = repository.getCommunity(id)
                if (result.exception is Exception) {
                    throw result.exception!!
                } else {
                    emit(result.data!!)
                }
            }.asResult()
                .map{ result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.update {false}
                    }
                    is Result.Error -> {
                        _errState.update { "Error : ${result.exception}" }
                    }
                    is Result.Success -> {
                        val data = result.data
                        _title.update{ data.title }
                        _content.update { data.content }
                        _category.update {
                            val category = when(it?.name) {
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
                        _pictures.update{ data.communityPhotos}
                        _isLoading.update{true}
                    }
                }
            }
        }
    }

    //title update
    fun updateTitle(title : String){
        _title.update{ title }
    }

    //content update
    fun updateContent(content : String) {
        _content.update{ content }
    }

    //id setting
    fun setId(id : Int) {
        _id.update { id }

        //id 기반으로 정보를 가져옴
        getCommunityDescription(id)
    }

    /** 사진 추가, 게시글 수정 어케하는 지 잘 모르겠네... */
    //picture 추가
    fun addPictures(newPicture : String){
        /** add pictrues 리스트에 newPicture uri 추가 */
    }

    fun delPictures(delPicture : Int){
        /** del pictures 리스트에 삭제할 picture의 id 추가 */
    }

    //게시글 수정
    fun updateCommunity(){
        viewModelScope.launch{
            if (id.value != null){
                repository.postCommunityUpdate(
                    images = addPictures.value,
                    title = title.value,
                    content = content.value,
                    communityId = id.value!!,
                    deleteCommunityPhotoIds = delPictures.value
                )
            } else {
                _errState.update{ "id is null" }
            }
        }
    }
}

sealed interface CommunityEditUiState {
    data object Loading : CommunityEditUiState
    data class Community(
        val title : String,
        val content : String,
        val category : Category?,
        val pictures : List<CommunityPhotoDefaultResponseDto>,
    ) : CommunityEditUiState
}