package com.hmoa.feature_perfume.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_common.handleErrorType
import com.hmoa.core_domain.repository.LoginRepository
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_domain.usecase.*
import com.hmoa.core_domain.entity.data.PerfumeGender
import com.hmoa.core_domain.entity.data.Weather
import com.hmoa.core_domain.entity.data.Perfume
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.PerfumeAgeResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.hmoa.core_model.response.PerfumeGenderResponseDto
import com.hmoa.core_model.response.PerfumeWeatherResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfumeViewmodel @Inject constructor(
    private val updatePerfumeAge: UpdatePerfumeAgeUseCase,
    private val updatePerfumeGender: UpdatePerfumeGenderUseCase,
    private val updatePerfumeWeather: UpdatePerfumeWeatherUseCase,
    private val getPerfume: GetPerfumeUsecase,
    private val updateLikePerfumeComment: UpdateLikePerfumeCommentUseCase,
    private val perfumeRepository: PerfumeRepository,
    private val reportRepository: ReportRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {
    private var authToken = MutableStateFlow<String?>(null)
    private var hasToken: Boolean = false
    private var perfumeState = MutableStateFlow<Perfume?>(null)
    private var weatherState = MutableStateFlow<PerfumeWeatherResponseDto?>(null)
    private var genderState = MutableStateFlow<PerfumeGenderResponseDto?>(null)
    private var ageState = MutableStateFlow<PerfumeAgeResponseDto?>(null)
    private var perfumeCommentsState = MutableStateFlow<List<PerfumeCommentResponseDto>?>(null)
    private var _perfumeCommentsCountState = MutableStateFlow<Int>(0)
    val perfumeCommentsCountState: StateFlow<Int> = _perfumeCommentsCountState
    private var _perfumeCommentIdStateToReport = MutableStateFlow<Int?>(null)
    val perfumeCommentIdStateToReport = _perfumeCommentIdStateToReport

    val uiState: StateFlow<PerfumeUiState> =
        combine(
            weatherState,
            genderState,
            ageState,
            perfumeState,
            perfumeCommentsState,
        ) { weather, gender, age, perfume, perfumeComments ->
            PerfumeUiState.PerfumeData(
                data = perfume,
                weather = weather,
                age = age,
                gender = gender,
                perfumeComments = perfumeComments
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PerfumeUiState.Loading
            )

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

    init {
        getAuthToken()
    }

    fun getHasToken(): Boolean {
        return hasToken
    }

    private fun getAuthToken() {
        viewModelScope.launch {
            loginRepository.getAuthToken().onEmpty { }.collectLatest {
                Log.d("PerfumeViewmodel", "${it}")
                authToken.value = it
                if (it != null) {
                    hasToken = true
                }
            }
        }
    }

    fun notifyLoginNeed() {
        unLoginedErrorState.update { true }
    }

    fun onChangePerfumeAge(age: Float, perfumeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (hasToken) {
                onUpdatePerfumeAge(age, perfumeId)
            } else {
                unLoginedErrorState.update { true }
            }
        }
    }

    suspend fun onUpdatePerfumeAge(age: Float, perfumeId: Int) {
        updatePerfumeAge(age = age, perfumeId).asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    ageState.update { result.data.data }
                }

                is Result.Error -> {
                    handleErrorType(
                        error = result.exception,
                        onExpiredTokenError = { expiredTokenErrorState.update { true } },
                        onWrongTypeTokenError = { wrongTypeTokenErrorState.update { true } },
                        onUnknownError = { unLoginedErrorState.update { true } },
                        onGeneralError = { generalErrorState.update { Pair(true, result.exception.message) } }
                    )
                }

                is Result.Loading -> {}
            }
        }
    }

    fun onChangePerfumeGender(gender: PerfumeGender, perfumeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (hasToken) {
                onUpdatePerfumeGender(gender, perfumeId)
            } else {
                unLoginedErrorState.update { true }
            }
        }
    }

    suspend fun onUpdatePerfumeGender(gender: PerfumeGender, perfumeId: Int) {
        updatePerfumeGender(gender, perfumeId).asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    genderState.update { result.data.data }
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }

    fun onChangePerfumeWeather(weather: Weather, perfumeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (hasToken) {
                onUpdatePerfumeWeather(weather, perfumeId)
            } else {
                unLoginedErrorState.update { true }
            }
        }
    }

    suspend fun onUpdatePerfumeWeather(weather: Weather, perfumeId: Int) {
        updatePerfumeWeather(weather, perfumeId).asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    weatherState.update { result.data.data }
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }

    fun initializePerfume(perfumeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPerfume(perfumeId.toString()).asResult().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        perfumeState.update { result.data.data }
                        perfumeCommentsState.update { result.data.data?.commentInfo?.comments }
                        _perfumeCommentsCountState.update { result.data.data?.commentInfo?.commentCount ?: 0 }
                    }

                    is Result.Loading -> {
                        PerfumeUiState.Loading
                    }

                    is Result.Error -> {
                        handleErrorType(
                            error = result.exception,
                            onExpiredTokenError = { expiredTokenErrorState.update { true } },
                            onWrongTypeTokenError = { wrongTypeTokenErrorState.update { true } },
                            onUnknownError = { unLoginedErrorState.update { true } },
                            onGeneralError = { generalErrorState.update { Pair(true, result.exception.message) } }
                        )
                    }
                }
            }
        }
    }

    fun onBackAgeToZero() {
        val result = PerfumeAgeResponseDto(age = 0f, writed = true)
        ageState.update { result }
    }

    fun updateLike(like: Boolean, perfumeId: Int) {
        if (hasToken) {
            when (like) {
                true -> viewModelScope.launch { putPerfumeLike(like, perfumeId) }
                false -> viewModelScope.launch { deletePerfumeLike(like, perfumeId) }
            }
        } else {
            unLoginedErrorState.update { true }
        }
    }

    suspend fun putPerfumeLike(like: Boolean, perfumeId: Int) {
        flow { emit(perfumeRepository.putPerfumeLike(perfumeId.toString())) }.asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    perfumeState.value?.liked = like
                    val newValue = perfumeState.value?.likedCount!! + 1
                    perfumeState.value?.likedCount = newValue
                }

                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }
    }

    suspend fun deletePerfumeLike(like: Boolean, perfumeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            flow { emit(perfumeRepository.deletePerfumeLike(perfumeId.toString())) }.asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            perfumeState.value?.liked = like
                            perfumeState.value?.likedCount = perfumeState.value?.likedCount!!.minus(1)
                        }

                        is Result.Loading -> {}
                        is Result.Error -> {}
                    }
                }
        }
    }

    fun updatePerfumeCommentLike(like: Boolean, commentId: Int, index: Int) {
        if (hasToken) {
            val oldPerfumeComments = perfumeState.value?.commentInfo?.comments
            val newPerfumeComment = updatePerfumeCommentState(index, like, oldPerfumeComments)
            perfumeCommentsState.value = perfumeCommentsState.value?.map { comment ->
                if (comment.id == newPerfumeComment.id) newPerfumeComment else comment
            }
            viewModelScope.launch(Dispatchers.IO) {
                updateLikePerfumeComment(like, commentId)
            }
        } else {
            unLoginedErrorState.update { true }
        }
    }

    fun updatePerfumeCommentState(
        index: Int,
        like: Boolean,
        oldPerfumeComments: List<PerfumeCommentResponseDto>?,
    ): PerfumeCommentResponseDto {
        val perfumeComments = oldPerfumeComments?.toMutableList()
        var likeTargetPerfumeComment = oldPerfumeComments?.get(index)
        var heartCount = likeTargetPerfumeComment?.heartCount ?: 0

        when (like) {
            true -> {
                likeTargetPerfumeComment?.heartCount = heartCount + 1
            }

            false -> {
                likeTargetPerfumeComment?.heartCount = heartCount - 1
            }
        }
        likeTargetPerfumeComment?.liked = like
        perfumeComments?.set(index, likeTargetPerfumeComment!!)
        return likeTargetPerfumeComment!!
    }

    fun updatePerfumeCommentIdToReport(id: Int) {
        _perfumeCommentIdStateToReport.value = id
    }

    fun reportPerfumeComment(id: Int?) {
        if (hasToken) {
            if (id != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    reportRepository.reportPerfumeComment(TargetRequestDto(id.toString()))
                }
            }
        } else {
            unLoginedErrorState.update { true }
        }
    }


    sealed interface PerfumeUiState {
        data object Loading : PerfumeUiState
        data class PerfumeData(
            val data: Perfume?,
            val weather: PerfumeWeatherResponseDto?,
            val gender: PerfumeGenderResponseDto?,
            val age: PerfumeAgeResponseDto?,
            val perfumeComments: List<PerfumeCommentResponseDto>?,
        ) : PerfumeUiState

        data object Empty : PerfumeUiState
    }
}