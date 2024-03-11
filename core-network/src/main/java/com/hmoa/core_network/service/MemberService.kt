package com.hmoa.core_network.service

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.*
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface MemberService {
    @GET("/member")
    suspend fun getMember(): MemberResponseDto

    @PATCH("/member/age")
    suspend fun updateAge(@Body request: AgeRequestDto): DataResponseDto<Any>

    @GET("/member/communities")
    suspend fun getCommunities(@Query("page") page: Int): List<CommunityByCategoryResponseDto>

    @GET("/member/communityComments")
    suspend fun getCommunityComments(@Query("page") page: Int): List<CommunityCommentDefaultResponseDto>

    @GET("/member/communityHearts")
    suspend fun getCommunityFavoriteComments(@Query("page") page: Int): List<CommunityCommentDefaultResponseDto>

    @DELETE("/member/delete")
    suspend fun deleteMember(): DataResponseDto<Any>

    @POST("/member/existsnickname/")
    suspend fun postExistsNickname(@Body request: NickNameRequestDto): ApiResponse<Boolean>

    @PATCH("/member/join")
    suspend fun updateJoin(@Body request: JoinUpdateRequestDto): MemberResponseDto

    @PATCH("/member/nickname")
    suspend fun updateNickname(@Body request: NickNameRequestDto): DataResponseDto<Any>

    @GET("/member/perfumeComments")
    suspend fun getPerfumeComments(@Query("page") page: Int): List<CommunityCommentDefaultResponseDto>

    @GET("/member/perfumeHearts")
    suspend fun getPerfumeFavoriteComments(@Query("page") page : Int) : List<CommunityCommentDefaultResponseDto>

    @FormUrlEncoded
    @POST("/member/profile-photo")
    suspend fun postProfilePhoto(@Field("image") image: String): DataResponseDto<Any>


    @DELETE("/member/profile-photo")
    suspend fun deleteProfilePhoto(): DataResponseDto<Any>

    @PATCH("/member/sex")
    suspend fun updateSex(@Body request: SexRequestDto): DataResponseDto<Any>
}