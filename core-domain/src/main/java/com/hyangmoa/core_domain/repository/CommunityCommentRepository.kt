package com.hyangmoa.core_domain.repository

import ResultResponse
import com.hyangmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hyangmoa.core_model.response.CommunityCommentAllResponseDto
import com.hyangmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hyangmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.PagingData

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