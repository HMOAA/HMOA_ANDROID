package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PagingData

interface CommunityCommentRepository {
    suspend fun getCommunityComment(
        commentId : Int
    ) : ResultResponse<CommunityCommentWithLikedResponseDto>

    suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto>

    suspend fun deleteCommunityComment(commentId: Int): ResultResponse<DataResponseDto<Any>>

    suspend fun putCommunityCommentLiked(commentId: Int): ResultResponse<DataResponseDto<Any>>

    suspend fun deleteCommunityCommentLiked(commentId: Int): ResultResponse<DataResponseDto<Any>>
    suspend fun getAllCommunityComment(communityId: Int, cursor: Int): CommunityCommentAllResponseDto
    suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto>
    suspend fun getMyCommunityCommentsByHeart(cursor : Int) : ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>
    suspend fun getMyCommunityComments(cursor : Int) : ResultResponse<PagingData<CommunityCommentDefaultResponseDto>>
}