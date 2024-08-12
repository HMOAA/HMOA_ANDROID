package com.hyangmoa.core_repository

import ResultResponse
import com.hyangmoa.core_datastore.CommunityComment.CommunityCommentDataStore
import com.hyangmoa.core_domain.repository.CommunityCommentRepository
import com.hyangmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hyangmoa.core_model.response.CommunityCommentAllResponseDto
import com.hyangmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hyangmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.PagingData
import javax.inject.Inject

class CommunityCommentRepositoryImpl @Inject constructor(private val communityCommentDataStore: CommunityCommentDataStore)
    : CommunityCommentRepository {
    override suspend fun getCommunityComment(
        commentId : Int
    ) : ResultResponse<CommunityCommentWithLikedResponseDto> {
        return communityCommentDataStore.getCommunityComment(commentId)
    }
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
    ): ResultResponse<DataResponseDto<Any>>  {
        return communityCommentDataStore.putCommunityCommentLiked(commentId)
    }

    override suspend fun deleteCommunityCommentLiked(commentId: Int): ResultResponse<DataResponseDto<Any>> {
        return communityCommentDataStore.deleteCommunityCommentLiked(commentId)
    }
    override suspend fun getAllCommunityComment(communityId: Int, cursor: Int): CommunityCommentAllResponseDto {
        return communityCommentDataStore.getAllCommunityComment(communityId, cursor)
    }

    override suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto> {
        return communityCommentDataStore.postCommunityComment(communityId, dto)
    }

    override suspend fun getMyCommunityCommentsByHeart(cursor: Int): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>> {
        return communityCommentDataStore.getMyCommunityCommentsByHeart(cursor)
    }

    override suspend fun getMyCommunityComments(cursor: Int): ResultResponse<PagingData<CommunityCommentDefaultResponseDto>> {
        return communityCommentDataStore.getMyCommunityComments(cursor)
    }
}