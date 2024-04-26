package com.hmoa.core_network.service

import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PagingData
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface PerfumeCommentService {

    @GET("/perfume/comments/{commentId}")
    suspend fun getPerfumeComment(@Path("commentId") commentId: Int): PerfumeCommentResponseDto

    @GET("perfume/{perfumeId}/comments/cursor")
    suspend fun getPerfumeCommentsLatest(
        @Path("perfumeId") perfumeId: Int,
        @Query("page") page: Int,
        @Query("cursor") cursor: Int,
    ): PerfumeCommentGetResponseDto

    @POST("perfume/{perfumeId}/comments")
    suspend fun postPerfumeComment(
        @Path("perfumeId") perfumeId: Int,
        @Body dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto

    @GET("perfume/{perfumeId}/comments/top")
    suspend fun getPerfumeCommentsLikest(
        @Path("perfumeId") perfumeId: String,
        @Query("page") page: String,
    ): PerfumeCommentGetResponseDto

    @DELETE("/perfume/comments/{comments}/delete")
    suspend fun deletePerfumeComments(@Path("commentId") commentId: Int): DataResponseDto<Any>

    @PUT("/perfume/comments/{commentId}/like")
    suspend fun putPerfumeCommentLike(@Path("commentId") commentId: Int): ApiResponse<DataResponseDto<Nothing?>>

    @DELETE("/perfume/comments/{commentId}/like")
    suspend fun deletePerfumeCommentLike(@Path("commentId") commentId: Int): ApiResponse<DataResponseDto<Nothing?>>

    @PUT("/perfume/comments/{comments}/modify")
    suspend fun putPerfumeCommentModify(
        @Path("commentId") commentId: Int,
        @Body dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto

    @GET("/perfume/comments/like")
    suspend fun getPerfumeCommentsWithHeart(
        @Query("cursor") cursor : Int,
    ) : ApiResponse<PagingData<CommunityCommentDefaultResponseDto>>

    @GET("/perfume/comments/me")
    suspend fun getMyPerfumeComments(
        @Query("cursor") cursor : Int,
    ) : ApiResponse<PagingData<CommunityCommentDefaultResponseDto>>
}