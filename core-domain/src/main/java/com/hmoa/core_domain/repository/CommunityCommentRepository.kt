package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.DataResponseDto

interface CommunityCommentRepository {
    suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto>

    suspend fun deleteCommunityComment(commentId: Int): ResultResponse<DataResponseDto<Any>>

    suspend fun putCommunityCommentLiked(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<DataResponseDto<Any>>

    suspend fun deleteCommunityCommentLiked(commentId: Int): ResultResponse<DataResponseDto<Any>>
    suspend fun getCommunityComments(communityId: Int, page: Int): ResultResponse<CommunityCommentAllResponseDto>
    suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto>
}