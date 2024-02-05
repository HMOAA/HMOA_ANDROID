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

class MemberDataStoreImpl : MemberDataStore {
    override fun getMember(): MemberResponseDto {
        TODO("Not yet implemented")
    }

    override fun updateAge(request: AgeRequestDto): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun getCommunities(page: Int): List<CommunityByCategoryResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getCommunityComments(page: Int): List<CommunityCommentByMemberResponseDto> {
        TODO("Not yet implemented")
    }

    override fun deleteMember(): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postExistsNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun getHearts(page: Int): List<CommunityCommentDefaultResponseDto> {
        TODO("Not yet implemented")
    }

    override fun updateJoin(request: JoinUpdateRequestDto): MemberResponseDto {
        TODO("Not yet implemented")
    }

    override fun updateNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun getPerfumeComments(page: Int): List<PerfumeCommentResponseDto> {
        TODO("Not yet implemented")
    }

    override fun postProfilePhoto(image: File): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun deleteProfilePhoto(): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun updateSex(request: SexRequestDto): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }
}