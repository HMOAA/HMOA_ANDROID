package com.hmoa.feature_perfume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.usecase.GetPerfumeUsecase
import com.hmoa.core_domain.usecase.UpdatePerfumeAgeUseCase
import com.hmoa.core_domain.usecase.UpdatePerfumeGenderUseCase
import com.hmoa.core_domain.usecase.UpdatePerfumeWeatherUseCase
import com.hmoa.core_model.PerfumeGender
import com.hmoa.core_model.Weather
import com.hmoa.core_model.data.Perfume
import com.hmoa.core_model.response.PerfumeAgeResponseDto
import com.hmoa.core_model.response.PerfumeGenderResponseDto
import com.hmoa.core_model.response.PerfumeWeatherResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfumeViewmodel @Inject constructor(
    private val updatePerfumeAge: UpdatePerfumeAgeUseCase,
    private val updatePerfumeGender: UpdatePerfumeGenderUseCase,
    private val updatePerfumeWeather: UpdatePerfumeWeatherUseCase,
    private val getPerfume: GetPerfumeUsecase
) : ViewModel() {
    private val perfumeState = MutableStateFlow<Perfume?>(null)
    private val weatherState = MutableStateFlow<PerfumeWeatherResponseDto?>(null)
    private val genderState = MutableStateFlow<PerfumeGenderResponseDto?>(null)
    private val ageState = MutableStateFlow<PerfumeAgeResponseDto?>(null)
    private val scope = CoroutineScope(Dispatchers.IO)

    val uiState: StateFlow<PerfumeUiState> =
        combine(weatherState, genderState, ageState, perfumeState) { weather, gender, age, perfume ->
            PerfumeUiState.PerfumeData(
                data = perfume,
                weather = weather,
                age = age,
                gender = gender
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PerfumeUiState.Loading
            )


    fun onChangePerfumeAge(age: Float, perfumeId: Int) {
        scope.launch {
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
        scope.launch {
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
        scope.launch {
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
        scope.launch {
            getPerfume(perfumeId.toString()).asResult().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        perfumeState.update { result.data }
                    }

                    is Result.Loading -> {}
                    is Result.Error -> {}
                }
            }
        }
    }

    sealed interface PerfumeUiState {
        data object Loading : PerfumeUiState
        data class PerfumeData(
            val data: Perfume?,
            val weather: PerfumeWeatherResponseDto?,
            val gender: PerfumeGenderResponseDto?,
            val age: PerfumeAgeResponseDto?
        ) : PerfumeUiState

        data object Empty : PerfumeUiState
    }

}