package com.hmoa.core_network.service

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberService {
    suspend fun getMember(): MemberResponseDto
    suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any>
    suspend fun getCommunities(page: Int): List<CommunityByCategoryResponseDto>
    suspend fun getCommunityComments(page: Int): List<CommunityCommentByMemberResponseDto>
    suspend fun deleteMember(): DataResponseDto<Any>

    @POST("/member/existsnickname")
    suspend fun postExistsNickname(@Body request: NickNameRequestDto): Call<Boolean>
    suspend fun getHearts(page: Int): List<CommunityCommentDefaultResponseDto>
    suspend fun updateJoin(request: JoinUpdateRequestDto): MemberResponseDto
    suspend fun updateNickname(request: NickNameRequestDto): DataResponseDto<Any>
    suspend fun getPerfumeComments(page: Int): List<PerfumeCommentResponseDto>
    suspend fun postProfilePhoto(image: String): DataResponseDto<Any>
    suspend fun deleteProfilePhoto(): DataResponseDto<Any>
    suspend fun updateSex(request: SexRequestDto): DataResponseDto<Any>
}