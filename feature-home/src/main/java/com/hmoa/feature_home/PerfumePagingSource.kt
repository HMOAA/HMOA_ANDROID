package com.hmoa.feature_home

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PerfumePagingSource<T : Any, R>(
    private val fetcher: suspend (pageNumber: Int) -> R,
    private val mapper: (R) -> List<T>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pageNumber = params.key ?: 0
        return try {
            val response = fetcher(pageNumber)
            val data = mapper(response)

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (data.isEmpty()) null else pageNumber + 1

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
