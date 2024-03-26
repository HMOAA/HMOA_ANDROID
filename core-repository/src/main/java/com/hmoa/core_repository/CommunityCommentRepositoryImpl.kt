package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.CommunityComment.CommunityCommentDataStore
import com.hmoa.core_domain.repository.CommunityCommentRepository
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class CommunityCommentRepositoryImpl @Inject constructor(private val communityCommentDataStore: CommunityCommentDataStore)
    : CommunityCommentRepository {
    override suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto> {
        return communityCommentDataStore.putCommunityComment(commentId, dto)
    }

    override suspend fun deleteCommunityComment(commentId: Int): ResultResponse<DataResponseDto<Any>> {
        return communityCommentDataStore.deleteCommunityComment(commentId)
    }

    override suspend fun putCommunityCommentLiked(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<DataResponseDto<Any>>  {
        return communityCommentDataStore.putCommunityCommentLiked(commentId, dto)
    }

    override suspend fun deleteCommunityCommentLiked(commentId: Int): ResultResponse<DataResponseDto<Any>> {
        return communityCommentDataStore.deleteCommunityCommentLiked(commentId)
    }
    override suspend fun getCommunityComments(communityId: Int, page: Int): ResultResponse<CommunityCommentAllResponseDto> {
        return communityCommentDataStore.getCommunityComments(communityId, page)
    }

    override suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto> {
        return communityCommentDataStore.postCommunityComment(communityId, dto)
    }
}