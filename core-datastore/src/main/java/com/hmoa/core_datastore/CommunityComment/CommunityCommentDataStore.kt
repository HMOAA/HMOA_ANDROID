package com.hmoa.core_datastore.CommunityComment

import ResultResponse
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.DataResponseDto

interface CommunityCommentDataStore {
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
}