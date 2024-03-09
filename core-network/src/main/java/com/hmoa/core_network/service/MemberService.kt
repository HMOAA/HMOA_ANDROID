package com.hmoa.core_network.service

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.*
import retrofit2.Call
import retrofit2.http.*

interface MemberService {
    @GET("/member")
    suspend fun getMember(): MemberResponseDto

    @PATCH("/member/age")
    suspend fun updateAge(@Body request: AgeRequestDto): DataResponseDto<Any>

    @GET("/member/communities")
    suspend fun getCommunities(@Field("page") page: Int): List<CommunityByCategoryResponseDto>

    @GET("/member/communityComments")
    suspend fun getCommunityComments(@Field("page") page: Int): List<CommunityCommentByMemberResponseDto>

    @DELETE("/member/delete")
    suspend fun deleteMember(): DataResponseDto<Any>

    @FormUrlEncoded
    @POST("/member/existsnickname")
    suspend fun postExistsNickname(@Body request: NickNameRequestDto): Call<Boolean>

    @GET("/member/hearts")
    suspend fun getHearts(@Field("page") page: Int): List<CommunityCommentDefaultResponseDto>

    @PATCH("/member/join")
    suspend fun updateJoin(@Body request: JoinUpdateRequestDto): MemberResponseDto

    @PATCH("/member/nickname")
    suspend fun updateNickname(@Body request: NickNameRequestDto): DataResponseDto<Any>

    @GET("/member/perfumeComments")
    suspend fun getPerfumeComments(@Field("page") page: Int): List<PerfumeCommentResponseDto>

    @FormUrlEncoded
    @POST("/member/profile-photo")
    suspend fun postProfilePhoto(@Field("image") image: String): DataResponseDto<Any>


    @DELETE("/member/profile-photo")
    suspend fun deleteProfilePhoto(): DataResponseDto<Any>

    @PATCH("/member/sex")
    suspend fun updateSex(@Body request: SexRequestDto): DataResponseDto<Any>
}