package com.hmoa.feature_hbti.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.absolutePath
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.HShopReviewRepository
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
class EditReviewViewModel @Inject constructor(
    private val hShopReviewRepository: HShopReviewRepository
): ViewModel(){

    private val id = MutableStateFlow<Int?>(null)
    private val _isDone = MutableStateFlow<Boolean>(false)
    val isDone get() = _isDone.asStateFlow()

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

    val uiState: StateFlow<EditReviewUiState> = errorUiState.map{errState ->
        if (errState is ErrorUiState.ErrorData && errState.isValidate()) throw Exception("")
        hShopReviewRepository.getReview(id.value!!)
    }.asResult().map{ result ->
        when(result){
            Result.Loading -> EditReviewUiState.Loading
            is Result.Success -> {
                val data = result.data.data!!
                EditReviewUiState.Success(
                    photoUris = data.hbtiPhotos.map{it.photoUrl},
                    photoIds = data.hbtiPhotos.map{it.photoId},
                    content = data.content
                )
            }
            is Result.Error -> {
                if (result.exception.message != ""){
                    handleErrorType(
                        error = result.exception,
                        onExpiredTokenError = {expiredTokenErrorState.update{true}},
                        onWrongTypeTokenError = {wrongTypeTokenErrorState.update{true}},
                        onUnknownError = {unLoginedErrorState.update{true}},
                        onGeneralError = {generalErrorState.update{Pair(true, result.exception.message)}}
                    )
                }
                EditReviewUiState.Error
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = EditReviewUiState.Loading
    )

    fun setId(newId: Int?){
        if (newId == null) {
            generalErrorState.update { Pair(true, "해당 리뷰를 찾을 수 없습니다.") }
            return
        }
        id.update{newId}
    }

    fun editReview(
        images: List<String>,
        content: String
    ){
        viewModelScope.launch{
            val totalImages = images.map{File(it)}
            val deleteImages = (uiState.value as EditReviewUiState.Success).photoUris.zip((uiState.value as EditReviewUiState.Success).photoIds).filterNot{
                it.first in images
            }.map{it.second}
            val result = hShopReviewRepository.postEditReview(
                image = totalImages.toTypedArray(),
                deleteReviewPhotoIds = deleteImages.toTypedArray(),
                content = content,
                reviewId = id.value!!,
            )
            Log.d("TAG TEST", "result : ${result}")
            if(result.errorMessage != null){
                when(result.errorMessage!!.message){
                    ErrorMessageType.UNKNOWN_ERROR.name -> {unLoginedErrorState.update{true}}
                    ErrorMessageType.EXPIRED_TOKEN.name -> {expiredTokenErrorState.update{true}}
                    ErrorMessageType.WRONG_TYPE_TOKEN.name -> {wrongTypeTokenErrorState.update{true}}
                    else -> {generalErrorState.update{Pair(true, result.errorMessage!!.message)}}
                }
                return@launch
            }
            _isDone.update{true}
        }
    }

    fun transformUriToString(context: Context, uri: Uri): String = absolutePath(context, uri) ?: ""
}

sealed interface EditReviewUiState{
    data object Loading: EditReviewUiState
    data object Error: EditReviewUiState
    data class Success(
        val photoUris: List<String>,
        val photoIds: List<Int>,
        val content: String
    ): EditReviewUiState
}