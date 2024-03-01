package com.hmoa.core_repository.CommunityComment

import com.hmoa.core_datastore.CommunityComment.CommunityCommentDataStore
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class CommunityCommentRepositoryImpl @Inject constructor(private val communityCommentDataStore: CommunityCommentDataStore) :
    CommunityCommentRepository {
    override suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return communityCommentDataStore.putCommunityComment(commentId, dto)
    }

    override suspend fun deleteCommunityComment(commentId: Int): DataResponseDto<Any> {
        return communityCommentDataStore.deleteCommunityComment(commentId)
    }

    override suspend fun getCommunityComments(commentId: Int, page: String): CommunityCommentAllResponseDto {
        return communityCommentDataStore.getCommunityComments(commentId, page)
    }

    override suspend fun postCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return communityCommentDataStore.postCommunityComment(commentId, dto)
    }
}