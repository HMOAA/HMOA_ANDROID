package com.hmoa.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.repository.MainRepository
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val firstMenuWithBanner = MutableStateFlow<HomeMenuFirstResponseDto?>(null)
    private val secondMenu = MutableStateFlow<HomeMenuDefaultResponseDto?>(null)
    private val thirdMenu = MutableStateFlow<HomeMenuDefaultResponseDto?>(null)

    init {
        getFirstMenuWithBanner()
        getSecondMenu()
        getThirdMenu()
    }

    val uiState: StateFlow<HomeUiState> =
        combine(firstMenuWithBanner, secondMenu, thirdMenu) { firstMenuWithBanner, secondMenu, thirdMenu ->
            HomeUiState.HomeData(
                bannerImg = firstMenuWithBanner?.mainImage,
                bannerTitle = firstMenuWithBanner?.banner,
                firstMenu = firstMenuWithBanner?.firstMenu,
                secondMenu = secondMenu,
                thirdMenu = thirdMenu
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    fun getFirstMenuWithBanner() {
        viewModelScope.launch {
            flow { emit(mainRepository.getFirst()) }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        firstMenuWithBanner.update { it }
                    }

                    is Result.Error -> {}//TODO()
                    Result.Loading -> {}//TODO()
                }
            }
        }
    }

    fun getSecondMenu() {
        viewModelScope.launch {
            flow { emit(mainRepository.getSecond()) }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        secondMenu.update { it }
                    }

                    is Result.Error -> {}//TODO()
                    Result.Loading -> {}//TODO()
                }
            }
        }
    }

    //세번째는 전체보기 메뉴 밖에 없어서 어떻게 할지 모르겠음
    fun getThirdMenu() {
        viewModelScope.launch {
            //flow { emit(mainRepository.getThird()) }
        }
    }

    sealed interface HomeUiState {
        data object Loading : HomeUiState
        data class HomeData(
            val bannerImg: String?,
            val bannerTitle: String?,
            val firstMenu: HomeMenuDefaultResponseDto?,
            val secondMenu: HomeMenuDefaultResponseDto?,
            val thirdMenu: HomeMenuDefaultResponseDto?,
        ) : HomeUiState

        data object Error : HomeUiState
    }
}