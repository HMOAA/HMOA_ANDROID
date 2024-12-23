package com.hmoa.feature_userinfo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto

class CommunityPagingSource(
    private val communityRepository: CommunityRepository
) : PagingSource<Int, CommunityByCategoryResponseDto>() {

    private var totalCount = 0
    private var cursor = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityByCategoryResponseDto> {
        val pageNumber = params.key ?: 0
        val response = communityRepository.getMyCommunities(cursor)
        if (response.errorMessage != null) {
            return LoadResult.Error(Exception(response.errorMessage!!.message))
        }
        val result = response.data!!
        totalCount = result.data.size
        cursor = if (totalCount == 0) 0 else result.data.last().communityId
        val prevKey = if (pageNumber > 0) pageNumber - 1 else null
        val nextKey = if (result.lastPage) null else pageNumber + 1

        return LoadResult.Page(
            data = result.data,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CommunityByCategoryResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}