package com.hmoa.core_datastore.CommunityComment

import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.CommunityCommentService
import javax.inject.Inject

class CommunityCommentDataStoreImpl @Inject constructor(private val communityCommentService: CommunityCommentService) :
    CommunityCommentDataStore {
    override suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentWithLikedResponseDto {
        return communityCommentService.putCommunityComment(commentId, dto)
    }

    override suspend fun deleteCommunityComment(commentId: Int): DataResponseDto<Any> {
        return communityCommentService.deleteCommunityComment(commentId)
    }

    override suspend fun putCommunityCommentLiked(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): DataResponseDto<Any>  {
        return communityCommentService.putCommunityCommentLiked(commentId, dto)
    }

    override suspend fun deleteCommunityCommentLiked(commentId: Int): DataResponseDto<Any> {
        return communityCommentService.deleteCommunityCommentLiked(commentId)
    }

    override suspend fun getCommunityComments(
        communityId: Int,
        page: Int
    ): CommunityCommentAllResponseDto {
        return communityCommentService.getCommunityComments(communityId, page)
    }

    override suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentWithLikedResponseDto {
        return communityCommentService.postCommunityComment(communityId, dto)
    }
}