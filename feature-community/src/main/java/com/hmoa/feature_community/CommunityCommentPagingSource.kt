package com.hmoa.feature_community

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto

class CommunityCommentPagingSource(
    private val communityCommentRepository : CommunityCommentRepository,
    private val id : Int,
) : PagingSource<Int, CommunityCommentWithLikedResponseDto>() {

    private var communityCount = 0
    private var cursor = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityCommentWithLikedResponseDto> {
        val pageNumber = params.key ?: 0
        try {
            val response =
                communityCommentRepository.getCommunityComments(
                    communityId = id,
                    page = cursor
                )
            communityCount = response.commentCount
            cursor = response.comments.last().commentId
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.lastPage) null else pageNumber + 1

            return LoadResult.Page(
                data = response.comments,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CommunityCommentWithLikedResponseDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}