package com.hmoa.core_network.service

import com.hmoa.core_model.request.PerfumeCommentRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import retrofit2.http.*

interface PerfumeCommentService {

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

    @PUT("/perfume/comments/{comments}/like")
    suspend fun putPerfumeCommentLike(@Path("commentId") commentId: Int): DataResponseDto<Any>

    @DELETE("/perfume/comments/{comments}/like")
    suspend fun deletePerfumeCommentLike(@Path("commentId") commentId: Int): DataResponseDto<Any>

    @PUT("/perfume/comments/{comments}/modify")
    suspend fun putPerfumeCommentModify(
        @Path("commentId") commentId: Int,
        @Body dto: PerfumeCommentRequestDto
    ): PerfumeCommentResponseDto
}