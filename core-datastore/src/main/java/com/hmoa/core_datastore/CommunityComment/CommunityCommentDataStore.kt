package com.hmoa.core_datastore.CommunityComment

import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.DataResponseDto

interface CommunityCommentDataStore {
    suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentWithLikedResponseDto

    suspend fun deleteCommunityComment(commentId: Int): DataResponseDto<Any>

    suspend fun putCommunityCommentLiked(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): DataResponseDto<Any>

    suspend fun deleteCommunityCommentLiked(commentId: Int): DataResponseDto<Any>

    suspend fun getCommunityComments(communityId: Int, page: String): CommunityCommentAllResponseDto
    suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentWithLikedResponseDto
}