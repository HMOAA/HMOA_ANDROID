package com.hmoa.feature_community

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto

class CommunityPagingSource(
    private val communityRepository : CommunityRepository,
    private val category : String,
) : PagingSource<Int, CommunityByCategoryResponseDto>() {

    private var cursor = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityByCategoryResponseDto> {
        val pageNumber = params.key ?: 0
        try {
            val response =
                communityRepository.getCommunityByCategory(
                    category = category,
                    cursor = cursor
                )
            if (response.exception is Exception){
                throw response.exception!!
            }
            val result = response.data!!
            cursor = result.communities.last().communityId

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (result.lastPage) null else pageNumber + 1

            return LoadResult.Page(
                data = result.communities,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CommunityByCategoryResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}