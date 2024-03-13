package com.hmoa.core_network.service

import com.hmoa.core_model.request.CommunityCommentDefaultRequestDto
import com.hmoa.core_model.response.CommunityCommentAllResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import retrofit2.http.*

interface CommunityCommentService {
    @PUT("/community/comment/{commentId}")
    suspend fun putCommunityComment(
        @Path(value = "commentId") commentId: Int,
        @Body dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto

    @DELETE("/community/comment/{commentId}")
    suspend fun deleteCommunityComment(
        @Path(value = "commentId") commentId: Int
    ): DataResponseDto<Any>

    @PUT("/community/comment/{commentId}/like")
    suspend fun putCommunityCommentLiked(
        @Path(value = "commentId") commentId: Int,
        @Body dto: CommunityCommentDefaultRequestDto
    ): DataResponseDto<Any>

    @DELETE("/community/comment/{commentId}/like")
    suspend fun deleteCommunityCommentLiked(
        @Path(value = "commentId") commentId: Int
    ): DataResponseDto<Any>

    @GET("/community/comment/{communityId}/findAll")
    suspend fun getCommunityComments(
        @Path(value = "communityId") communityId: Int,
        @Query("page") page: String
    ): CommunityCommentAllResponseDto

    @POST("/community/comment/{communityId}/save")
    suspend fun postCommunityComment(
        @Path(value = "communityId") commentId: Int,
        @Body dto: CommunityCommentDefaultRequestDto
    ): CommunityCommentDefaultResponseDto
}