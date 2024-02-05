package com.hmoa.core_repository.Member

import com.hmoa.core_datastore.Member.MemberDataStore
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

class MemberRepositoryImpl(
    private val memberDataStore: MemberDataStore
) : MemberRepository {

    override fun getMember(): MemberResponseDto {
        return memberDataStore.getMember()
    }

    override fun updateAge(request: AgeRequestDto): DataResponseDto<Any> {
        return memberDataStore.updateAge(request)
    }

    override fun getCommunities(page: Int): List<CommunityByCategoryResponseDto> {
        return memberDataStore.getCommunities(page)
    }

    override fun getCommunityComments(page: Int): List<CommunityCommentByMemberResponseDto> {
        return memberDataStore.getCommunityComments(page)
    }

    override fun deleteMember(): DataResponseDto<Any> {
        return memberDataStore.deleteMember()
    }

    override fun postExistsNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        return memberDataStore.postExistsNickname(request)
    }

    override fun getHearts(page: Int): List<CommunityCommentDefaultResponseDto> {
        return memberDataStore.getHearts(page)
    }

    override fun updateJoin(request: JoinUpdateRequestDto): MemberResponseDto {
        return memberDataStore.updateJoin(request)
    }

    override fun updateNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        return memberDataStore.updateNickname(request)
    }

    override fun getPerfumeComments(page: Int): List<PerfumeCommentResponseDto> {
        return memberDataStore.getPerfumeComments(page)
    }

    override fun postProfilePhoto(image: File): DataResponseDto<Any> {
        return memberDataStore.postProfilePhoto(image)
    }

    override fun deleteProfilePhoto(): DataResponseDto<Any> {
        return memberDataStore.deleteProfilePhoto()
    }

    override fun updateSex(request: SexRequestDto): DataResponseDto<Any> {
        return memberDataStore.updateSex(request)
    }
}