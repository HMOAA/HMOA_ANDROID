package com.hyangmoa.feature_brand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyangmoa.core_common.Result
import com.hyangmoa.core_common.asResult
import com.hyangmoa.core_domain.repository.SearchRepository
import com.hyangmoa.core_model.data.Consonant
import com.hyangmoa.core_model.response.BrandDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandSearchViewmodel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {
    val PAGE_SIZE = 10
    private var consonantBrandMapState =
        MutableStateFlow<MutableMap<Consonant?, List<BrandDefaultResponseDto>?>?>(null)
    private var searchWordState = MutableStateFlow<String>("")
    private var searchResultState = MutableStateFlow<MutableMap<Consonant?, List<BrandDefaultResponseDto>?>?>(null)
    var uiState: StateFlow<BrandSearchUiState> =
        combine(
            consonantBrandMapState,
            searchWordState,
            searchResultState
        ) { consonantsBrands, searchWord, searchResult ->
            BrandSearchUiState.Data(consonants = consonantsBrands, searchWord = searchWord, searchResult = searchResult)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BrandSearchUiState.Loading
        )

    init {
        initializeBrands()
    }

    fun initializeBrands() {
        viewModelScope.launch {
            for (i in 1..19) {
                flow { emit(searchRepository.getBrandAll(i)) }.asResult().collectLatest {
                    when (it) {
                        is Result.Success -> {
                            val newMap = consonantBrandMapState.value?.toMutableMap() ?: mutableMapOf()
                            newMap[mapNumberIntoConsonant(i)] = it.data.data
                            consonantBrandMapState.value = newMap
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    fun clearWord() {
        searchWordState.value = ""
    }

    fun searchBrandResult(word: String) {
        searchWordState.value = word
        searchResultState.value = mutableMapOf()
        viewModelScope.launch {
            flow { emit(searchRepository.getBrand(word)) }.asResult().collectLatest {
                when (it) {
                    is Result.Success -> {
                        it.data.data?.forEach {
                            val newMap = searchResultState.value?.toMutableMap()
                                ?: mutableMapOf()
                            newMap[mapNumberIntoConsonant(it.consonant)] = it.brandList
                            searchResultState.value = newMap
                        }
                    }

                    is Result.Error -> {

                    }

                    is Result.Loading -> {

                    }
                }
            }
        }
    }

    fun mapNumberIntoConsonant(number: Int): Consonant? {
        return when (number) {
            Consonant.ㄱ.id -> Consonant.ㄱ
            Consonant.ㄴ.id -> Consonant.ㄴ
            Consonant.ㄷ.id -> Consonant.ㄷ
            Consonant.ㄹ.id -> Consonant.ㄹ
            Consonant.ㅁ.id -> Consonant.ㅁ
            Consonant.ㅂ.id -> Consonant.ㅂ
            Consonant.ㅅ.id -> Consonant.ㅅ
            Consonant.ㅇ.id -> Consonant.ㅇ
            Consonant.ㅈ.id -> Consonant.ㅈ
            Consonant.ㅊ.id -> Consonant.ㅊ
            Consonant.ㅋ.id -> Consonant.ㅋ
            Consonant.ㅌ.id -> Consonant.ㅌ
            Consonant.ㅍ.id -> Consonant.ㅍ
            Consonant.ㅎ.id -> Consonant.ㅎ
            Consonant.ㄲ.id -> Consonant.ㄲ
            Consonant.ㄸ.id -> Consonant.ㄸ
            Consonant.ㅃ.id -> Consonant.ㅃ
            Consonant.ㅆ.id -> Consonant.ㅆ
            Consonant.ㅉ.id -> Consonant.ㅉ
            else -> null
        }
    }

    sealed interface BrandSearchUiState {
        data object Loading : BrandSearchUiState
        data class Data(
            var consonants: MutableMap<Consonant?, List<BrandDefaultResponseDto>?>?,
            var searchWord: String,
            var searchResult: MutableMap<Consonant?, List<BrandDefaultResponseDto>?>?,
        ) : BrandSearchUiState

        data object Error : BrandSearchUiState
    }

}