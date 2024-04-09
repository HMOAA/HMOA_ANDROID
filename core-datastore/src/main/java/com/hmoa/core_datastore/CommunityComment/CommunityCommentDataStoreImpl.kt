package com.hmoa.core_datastore.CommunityComment

import ResultResponse
import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.CommunityCommentService
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import javax.inject.Inject

class CommunityCommentDataStoreImpl @Inject constructor(private val communityCommentService: CommunityCommentService) :
    CommunityCommentDataStore {
    override suspend fun putCommunityComment(
        commentId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto> {
        val result = ResultResponse<CommunityCommentWithLikedResponseDto>()
        communityCommentService.putCommunityComment(commentId, dto).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun deleteCommunityComment(commentId: Int): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        communityCommentService.deleteCommunityComment(commentId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun putCommunityCommentLiked(
        commentId: Int
    ): ResultResponse<DataResponseDto<Any>>  {
        val result = ResultResponse<DataResponseDto<Any>>()
        communityCommentService.putCommunityCommentLiked(commentId).suspendMapSuccess{
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun deleteCommunityCommentLiked(commentId: Int): ResultResponse<DataResponseDto<Any>> {
        val result = ResultResponse<DataResponseDto<Any>>()
        communityCommentService.deleteCommunityCommentLiked(commentId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getCommunityComments(
        communityId: Int,
        page: Int
    ): ResultResponse<CommunityCommentAllResponseDto> {
        val result = ResultResponse<CommunityCommentAllResponseDto>()
        communityCommentService.getCommunityComments(communityId, page).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun postCommunityComment(
        communityId: Int,
        dto: CommunityCommentDefaultRequestDto
    ): ResultResponse<CommunityCommentWithLikedResponseDto> {
        val result = ResultResponse<CommunityCommentWithLikedResponseDto>()
        communityCommentService.postCommunityComment(communityId, dto).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }
}