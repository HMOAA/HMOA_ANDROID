package com.hmoa.feature_brand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmoa.core_common.Result
import com.hmoa.core_common.asResult
import com.hmoa.core_domain.entity.data.Consonant
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.response.BrandDefaultResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandSearchViewmodel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {
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
            val newMap = consonantBrandMapState.value?.toMutableMap() ?: mutableMapOf()
            for (i in 1..19) {
                flow { emit(searchRepository.getBrandAll(i)) }.asResult().collectLatest {
                    when (it) {
                        is Result.Success -> {
                            newMap[mapNumberIntoConsonant(i)] = it.data.data
                        }

                        else -> {}
                    }
                }
            }
            consonantBrandMapState.value = newMap
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
                        val newMap = searchResultState.value?.toMutableMap() ?: mutableMapOf()
                        it.data.data?.forEach {
                            newMap[mapNumberIntoConsonant(it.consonant)] = it.brandList
                        }
                        searchResultState.value = newMap
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
