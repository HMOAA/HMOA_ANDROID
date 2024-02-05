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
    fun getMember() : MemberResponseDto
    fun updateAge(request : AgeRequestDto) : DataResponseDto<Any>
    fun getCommunities(page : Int) : List<CommunityByCategoryResponseDto>
    fun getCommunityComments(page : Int) : List<CommunityCommentByMemberResponseDto>
    fun deleteMember() : DataResponseDto<Any>
    fun postExistsNickname(request : NickNameRequestDto) : DataResponseDto<Any>
    fun getHearts(page : Int) : List<CommunityCommentDefaultResponseDto>
    fun updateJoin(request : JoinUpdateRequestDto) : MemberResponseDto
    fun updateNickname(request : NickNameRequestDto) : DataResponseDto<Any>
    fun getPerfumeComments(page : Int) : List<PerfumeCommentResponseDto>
    fun postProfilePhoto(image : File) : DataResponseDto<Any> //file로 받는게 맞나?
    fun deleteProfilePhoto() : DataResponseDto<Any>
    fun updateSex(request : SexRequestDto) : DataResponseDto<Any>
}