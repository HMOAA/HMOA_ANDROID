package com.hmoa.feature_community.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CommunityPostViewModel @Inject constructor(
    private val repository : CommunityRepository
) : ViewModel() {

    //loading 여부 (true : 완료 / false : api event 처리 중)
    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

    //게시글 타입
    private val _category = MutableStateFlow(Category.추천)
    val category get() = _category.asStateFlow()

    //게시글 제목
    private val _title = MutableStateFlow("")
    val title get() = _title.asStateFlow()

    //게시글 내용
    private val _content = MutableStateFlow("")
    val content get() = _content.asStateFlow()

    //사진 배열
    private val _pictures = MutableStateFlow<ArrayList<File>>(arrayListOf())
    val pictures get() = _pictures.asStateFlow()

    //error state
    private val _errState = MutableStateFlow("")
    val errState get() = _errState.asStateFlow()

    //category 설정
    fun setCategory (newCategory : String) {
        when(newCategory){
            "시향기" -> _category.update{ Category.시향기 }
            "추천" -> _category.update {Category.추천}
            "자유" -> _category.update {Category.자유}
        }
    }

    //title update
    fun updateTitle (title : String) {
        _title.update{ title }
    }

    //content update
    fun updateContent(content : String) {
        _content.update{ content }
    }

    //사진 udpate
    fun updatePictures(newPictures : ArrayList<File>){
        _pictures.update{ newPictures }
    }

    //게시글 게시
    fun postCommunity(){

        viewModelScope.launch{
            //content, title 모두 isNotEmpty일 때
            if (content.value != ""){
                _errState.update{"내용을 입력해주세요"}
            } else if (title.value != "") {
                _errState.update {"제목을 입력해주세요"}
            } else {
                val images = pictures.value.toTypedArray()

                try{
                    _isLoading.update{false}
                    repository.postCommunitySave(
                        images = images,
                        category = category.value.name,
                        title = title.value,
                        content = content.value
                    )
                    _isLoading.update{true}
                } catch (e : Exception) {
                    _errState.update{ e.message ?: "" }
                }
            }
        }
    }

}