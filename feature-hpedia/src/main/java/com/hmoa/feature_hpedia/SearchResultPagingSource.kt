package com.hmoa.feature_hpedia

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.SearchRepository
import com.hmoa.core_model.response.NoteDefaultResponseDto
import com.hmoa.core_model.response.PerfumerDefaultResponseDto
import com.hmoa.core_model.response.TermDefaultResponseDto

class SearchResultPagingSource(
    private val searchRepository : SearchRepository,
    private val type : String?,
    private val searchWord : String,
) : PagingSource<Int, Any>() {

    private var cursor = 0

    private lateinit var result : List<Any>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        val pageNumber = params.key ?: 0
        try {
            when(type) {
                "용어" -> {
                    result = searchRepository.getTerm(
                        page = cursor,
                        searchWord = searchWord
                    )
                    cursor = (result as List<TermDefaultResponseDto>)[result.lastIndex].termId
                }
                "노트" -> {
                    result = searchRepository.getNote(
                        page = cursor,
                        searchWord = searchWord
                    )
                    cursor = (result as List<NoteDefaultResponseDto>)[result.lastIndex].noteId
                }
                "조향사" -> {
                    result = searchRepository.getPerfumer(
                        page = cursor,
                        searchWord = searchWord
                    )
                    cursor = (result as List<PerfumerDefaultResponseDto>)[result.lastIndex].perfumerId
                }
                else -> throw IllegalArgumentException("카테고리 오류")
            }

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (cursor <= 0) null else pageNumber + 1

            return LoadResult.Page(
                data = result,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}