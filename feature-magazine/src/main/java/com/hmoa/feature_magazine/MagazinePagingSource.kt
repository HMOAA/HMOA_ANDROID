package com.hmoa.feature_magazine

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_model.response.MagazineSummaryResponseDto

class MagazinePagingSource(
    private val magazineRepository: MagazineRepository,
) : PagingSource<Int, MagazineSummaryResponseDto>() {

    private var totalCount = 0
    private var cursor = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MagazineSummaryResponseDto> {
        val pageNumber = params.key ?: 0
        try {
            val response =
                magazineRepository.getMagazineList(
                    cursor = cursor
                ).data!!
            totalCount = response.data.size
            cursor = response.data.last().magazineId
            Log.d("Paging Source", "total : ${totalCount}, cursor : ${cursor}")
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.lastPage) null else pageNumber + 1

            return LoadResult.Page(
                data = response.data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MagazineSummaryResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}