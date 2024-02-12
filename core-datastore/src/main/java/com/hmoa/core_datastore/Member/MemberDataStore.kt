package com.hmoa.core_datastore.Member

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentByMemberResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import java.io.File

interface MemberDataStore {
    suspend fun getMember() : MemberResponseDto
    suspend fun updateAge(request : AgeRequestDto) : DataResponseDto<Any>
    suspend fun getCommunities(page : Int) : List<CommunityByCategoryResponseDto>
    suspend fun getCommunityComments(page : Int) : List<CommunityCommentByMemberResponseDto>
    suspend fun deleteMember() : DataResponseDto<Any>
    suspend fun postExistsNickname(request : NickNameRequestDto) : DataResponseDto<Any>
    suspend fun getHearts(page : Int) : List<CommunityCommentDefaultResponseDto>
    suspend fun updateJoin(request : JoinUpdateRequestDto) : MemberResponseDto
    suspend fun updateNickname(request : NickNameRequestDto) : DataResponseDto<Any>
    suspend fun getPerfumeComments(page : Int) : List<PerfumeCommentResponseDto>
    suspend fun postProfilePhoto(image : File) : DataResponseDto<Any> //file로 받는게 맞나?
    suspend fun deleteProfilePhoto() : DataResponseDto<Any>
    suspend fun updateSex(request : SexRequestDto) : DataResponseDto<Any>
}