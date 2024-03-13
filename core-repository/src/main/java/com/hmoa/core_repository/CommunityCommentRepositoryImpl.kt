package com.hmoa.core_repository

import com.hmoa.core_datastore.CommunityComment.CommunityCommentDataStore
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class CommunityCommentRepositoryImpl @Inject constructor(private val communityCommentDataStore: CommunityCommentDataStore) :
    com.hmoa.core_domain.repository.CommunityCommentRepository {
    override suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return communityCommentDataStore.putCommunityComment(commentId, dto)
    }

    override suspend fun deleteCommunityComment(commentId: Int): DataResponseDto<Any> {
        return communityCommentDataStore.deleteCommunityComment(commentId)
    }

    override suspend fun putCommunityCommentLiked(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return communityCommentDataStore.putCommunityCommentLiked(commentId, dto)
    }

    override suspend fun deleteCommunityCommentLiked(commentId: Int): DataResponseDto<Any> {
        return communityCommentDataStore.deleteCommunityCommentLiked(commentId)
    }

    override suspend fun getCommunityComments(communityId: Int, page: String): CommunityCommentAllResponseDto {
        return communityCommentDataStore.getCommunityComments(communityId, page)
    }

    override suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto {
        return communityCommentDataStore.postCommunityComment(communityId, dto)
    }
}