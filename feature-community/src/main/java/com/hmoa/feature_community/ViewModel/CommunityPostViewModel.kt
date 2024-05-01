package com.hmoa.feature_community.ViewModel

import android.app.Application
import android.net.Uri
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
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class CommunityPostViewModel @Inject constructor(
    private val application: Application,
    private val repository: CommunityRepository
) : ViewModel() {

    val context = application.applicationContext

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
    private val _pictures = MutableStateFlow<List<Uri>>(listOf())
    val pictures get() = _pictures.asStateFlow()

    //error state
    private val _errState = MutableStateFlow<String?>(null)
    val errState get() = _errState.asStateFlow()

    //category 설정
    fun setCategory(newCategory: String) {
        when (newCategory) {
            "시향기" -> _category.update { Category.시향기 }
            "추천" -> _category.update { Category.추천 }
            "자유" -> _category.update { Category.자유 }
        }
    }

    //title update
    fun updateTitle(title: String) {
        _title.update { title }
    }

    //content update
    fun updateContent(content: String) {
        _content.update { content }
    }

    //사진 update
    fun updatePictures(newPictures: List<Uri>) {
        _pictures.update {
            val result = arrayListOf<Uri>()
            newPictures.forEach {
                result.add(it)
            }
            result
        }
    }

    //사진 삭제
    fun deletePicture(idx: Int) {
        _pictures.update {
            val data = it
            data.minus(it[idx])
        }
    }

    //게시글 게시
    fun postCommunity() {
        val images = arrayListOf<File>()
        pictures.value.map { picture ->
            val path = absolutePath(picture) ?: throw NullPointerException("file path is NULL")
            images.add(File(path))
        }
        viewModelScope.launch {

            val result = repository.postCommunitySave(
                images = images.map { it.absoluteFile }.toTypedArray(),
                category = category.value.name,
                title = title.value,
                content = content.value
            )

            if (result.errorMessage != null) {
                _errState.update { "Exception : ${result.errorMessage!!.message}" }
            } else {
                result.data
            }
        }
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