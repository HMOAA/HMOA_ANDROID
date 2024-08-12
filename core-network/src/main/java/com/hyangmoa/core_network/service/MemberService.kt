package com.hyangmoa.core_network.service

import com.hyangmoa.core_model.request.AgeRequestDto
import com.hyangmoa.core_model.request.JoinUpdateRequestDto
import com.hyangmoa.core_model.request.NickNameRequestDto
import com.hyangmoa.core_model.request.SexRequestDto
import com.hyangmoa.core_model.response.CommunityByCategoryResponseDto
import com.hyangmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.MemberResponseDto
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface MemberService {
    @GET("/member")
    suspend fun getMember(): ApiResponse<MemberResponseDto>

    @PATCH("/member/age")
    suspend fun updateAge(@Body request: AgeRequestDto): DataResponseDto<Any>

    @GET("/member/communities")
    suspend fun getCommunities(@Query("page") page: Int): ApiResponse<List<CommunityByCategoryResponseDto>>

    @GET("/member/communityComments")
    suspend fun getCommunityComments(@Query("page") page: Int): ApiResponse<List<CommunityCommentDefaultResponseDto>>

    @GET("/member/communityHearts")
    suspend fun getCommunityFavoriteComments(@Query("page") page: Int): ApiResponse<List<CommunityCommentDefaultResponseDto>>

    @DELETE("/member/delete")
    suspend fun deleteMember(): DataResponseDto<Any>

    @POST("/member/existsnickname/")
    suspend fun postExistsNickname(@Body request: NickNameRequestDto): ApiResponse<Boolean>

    @PATCH("/member/join")
    suspend fun updateJoin(@Body request: JoinUpdateRequestDto): ApiResponse<MemberResponseDto>

    @PATCH("/member/nickname")
    suspend fun updateNickname(@Body request: NickNameRequestDto): ApiResponse<DataResponseDto<Any>>

    @GET("/member/perfumeComments")
    suspend fun getPerfumeComments(@Query("page") page: Int): ApiResponse<List<CommunityCommentDefaultResponseDto>>

    @GET("/member/perfumeHearts")
    suspend fun getPerfumeFavoriteComments(@Query("page") page: Int): ApiResponse<List<CommunityCommentDefaultResponseDto>>

    @Multipart
    @POST("/member/profile-photo")
    suspend fun postProfilePhoto(@Part image: MultipartBody.Part): ApiResponse<DataResponseDto<Any>>


    @DELETE("/member/profile-photo")
    suspend fun deleteProfilePhoto(): DataResponseDto<Any>

    @PATCH("/member/sex")
    suspend fun updateSex(@Body request: SexRequestDto): DataResponseDto<Any>
}