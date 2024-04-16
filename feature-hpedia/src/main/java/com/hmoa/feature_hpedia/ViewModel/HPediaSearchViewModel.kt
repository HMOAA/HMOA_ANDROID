package com.hmoa.feature_hpedia.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.feature_community.ViewModel.CommunitySearchUiState
import com.hmoa.feature_community.ViewModel.PAGE_SIZE
import com.hmoa.feature_hpedia.SearchResultPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HPediaSearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    //검색 카테고리?
    private val type = MutableStateFlow<String?>(null)

    //검색어
    private val _searchWord = MutableStateFlow("")
    val searchWord get() = _searchWord.asStateFlow()

    //상단바 상태
    private val _topBarState = MutableStateFlow(false)
    val topBarState get() = _topBarState.asStateFlow()

    //type 설정
    fun setType(newType : String?){
        type.update{ newType }
    }

    //상단바 상태 수정
    fun updateTopBarState(state : Boolean) {
        _topBarState.update{ state }
    }

    //검색어 변경
    fun updateSearchWord(newSearchWord : String){
        _searchWord.update{ newSearchWord }
    }

    //paging데이터 외부 노출
    fun communityPagingSource() : Flow<PagingData<Any>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            getSearchResultPaging()
        }
    ).flow.cachedIn(viewModelScope)

    //paging 가져오기
    private fun getSearchResultPaging() : SearchResultPagingSource{
        return SearchResultPagingSource(
            searchRepository = searchRepository,
            type = type.value,
            searchWord = searchWord.value
        )
    }
}