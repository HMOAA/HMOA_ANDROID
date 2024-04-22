package com.hmoa.feature_perfume.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_domain.repository.ReportRepository
import com.hmoa.core_domain.usecase.*
import com.hmoa.core_model.PerfumeGender
import com.hmoa.core_model.Weather
import com.hmoa.core_model.data.Perfume
import com.hmoa.core_model.request.TargetRequestDto
import com.hmoa.core_model.response.*
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
    private val reportRepository: ReportRepository
) : ViewModel() {
    private val perfumeState = MutableStateFlow<Perfume?>(null)
    private var weatherState = MutableStateFlow<PerfumeWeatherResponseDto?>(null)
    private var genderState = MutableStateFlow<PerfumeGenderResponseDto?>(null)
    private var ageState = MutableStateFlow<PerfumeAgeResponseDto?>(null)
    private var perfumeCommentDataState = MutableStateFlow<PerfumeCommentGetResponseDto?>(null)
    private var _perfumeCommentIdStateToReport = MutableStateFlow<Int?>(null)
    val perfumeCommentIdStateToReport = _perfumeCommentIdStateToReport

    val uiState: StateFlow<PerfumeUiState> =
        combine(
            weatherState,
            genderState,
            ageState,
            perfumeState,
            perfumeCommentDataState
        ) { weather, gender, age, perfume, perfumeCommentData ->
            PerfumeUiState.PerfumeData(
                data = perfume,
                weather = weather,
                age = age,
                gender = gender,
                perfumeCommentData = perfumeCommentData
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PerfumeUiState.Loading
            )


    fun onChangePerfumeAge(age: Float, perfumeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updatePerfumeAge(age = age, perfumeId).asResult().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        ageState.update { result.data }
                    }

                    is Result.Error -> {}
                    is Result.Loading -> {}
                }
            }
        }
    }

    fun onChangePerfumeGender(gender: PerfumeGender, perfumeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updatePerfumeGender(gender, perfumeId).asResult().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        genderState.update { result.data }
                    }

                    is Result.Error -> {}
                    is Result.Loading -> {}
                }
            }
        }
    }

    fun onChangePerfumeWeather(weather: Weather, perfumeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updatePerfumeWeather(weather, perfumeId).asResult().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        weatherState.update { result.data }
                    }

                    is Result.Error -> {}
                    is Result.Loading -> {}
                }
            }
        }
    }

    fun initializePerfume(perfumeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPerfume(perfumeId.toString()).asResult().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        perfumeState.update { result.data }
                        perfumeCommentDataState.update { result.data.commentInfo }
                    }

                    is Result.Loading -> {}
                    is Result.Error -> {}
                }
            }
        }
    }

    fun onBackAgeToZero() {
        val result = PerfumeAgeResponseDto(age = 0f, writed = true)
        ageState.update { result }
    }

    fun updateLike(like: Boolean, perfumeId: Int) {
        when (like) {
            true -> {
                viewModelScope.launch { putPerfumeLike(like, perfumeId) }

            }

            false -> {
                viewModelScope.launch { deletePerfumeLike(like, perfumeId) }
            }
        }
    }

    suspend fun putPerfumeLike(like: Boolean, perfumeId: Int) {
        flow { emit(perfumeRepository.putPerfumeLike(perfumeId.toString())) }.asResult().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    perfumeState.value?.liked = like
                    perfumeState.value?.likedCount = perfumeState.value?.likedCount!!.plus(1)
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
        viewModelScope.launch(Dispatchers.IO) {
            updateLikePerfumeComment(like, commentId).asResult().collectLatest {
                when (it) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        val oldPerfumeComments = perfumeState.value?.commentInfo?.comments
                        perfumeState.value?.commentInfo?.comments = listOf()
                        perfumeState.value?.commentInfo?.comments =
                            updatePerfumeCommentState(index, like, oldPerfumeComments) ?: emptyList()
                    }

                    is Result.Error -> {}
                }
            }
        }
    }

    fun updatePerfumeCommentState(
        index: Int,
        like: Boolean,
        oldPerfumeComments: List<PerfumeCommentResponseDto>?
    ): List<PerfumeCommentResponseDto>? {
        val likeTargetPerfumeComment = oldPerfumeComments?.get(index)
        val newPerfumeComments = oldPerfumeComments?.toMutableList()
        likeTargetPerfumeComment?.liked = like
        when (like) {
            true -> likeTargetPerfumeComment?.heartCount?.plus(1)
            false -> likeTargetPerfumeComment?.heartCount?.minus(-1)
        }
        newPerfumeComments?.set(index, likeTargetPerfumeComment!!)
        Log.d("PerfumeViewModel", "새 리스트: ${newPerfumeComments}")
        return newPerfumeComments?.toList()
    }

    fun updatePerfumeCommentIdToReport(id: Int) {
        _perfumeCommentIdStateToReport.value = id
    }

    fun reportPerfumeComment(id: Int?) {
        if (id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                reportRepository.reportPerfumeComment(TargetRequestDto(id.toString()))
            }
        }
    }


    sealed interface PerfumeUiState {
        data object Loading : PerfumeUiState
        data class PerfumeData(
            val data: Perfume?,
            val weather: PerfumeWeatherResponseDto?,
            val gender: PerfumeGenderResponseDto?,
            val age: PerfumeAgeResponseDto?,
            val perfumeCommentData: PerfumeCommentGetResponseDto?,
        ) : PerfumeUiState

        data object Empty : PerfumeUiState
    }

}