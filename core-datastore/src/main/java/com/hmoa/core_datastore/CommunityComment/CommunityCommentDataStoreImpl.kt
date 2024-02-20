package com.hmoa.core_datastore.CommunityComment

import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.CommunityComment.CommunityCommentService

internal class CommunityCommentDataStoreImpl constructor(private val communityCommentService: CommunityCommentService) :
    CommunityCommentDataStore {
    override suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return communityCommentService.putCommunityComment(commentId, dto)
    }

    override suspend fun deleteCommunityComment(commentId: Int): DataResponseDto<Any> {
        return communityCommentService.deleteCommunityComment(commentId)
    }

    override suspend fun getCommunityComments(commentId: Int, page: String): CommunityCommentAllResponseDto {
        return communityCommentService.getCommunityComments(commentId, page)
    }

    override suspend fun postCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return communityCommentService.postCommunityComment(commentId, dto)
    }
}