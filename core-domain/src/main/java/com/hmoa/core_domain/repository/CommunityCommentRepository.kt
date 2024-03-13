package com.hmoa.core_domain.repository

import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto

interface CommunityCommentRepository {
    suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto

    suspend fun deleteCommunityComment(commentId: Int): DataResponseDto<Any>

    suspend fun putCommunityCommentLiked(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto

    suspend fun deleteCommunityCommentLiked(commentId: Int): DataResponseDto<Any>
    suspend fun getCommunityComments(communityId: Int, page: String): CommunityCommentAllResponseDto
    suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto
}