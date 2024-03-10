package com.hmoa.core_datastore.Member

import android.util.Log
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.*
import com.hmoa.core_network.service.MemberService
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class MemberDataStoreImpl @Inject constructor(
    private val memberService: MemberService
) : MemberDataStore {
    override suspend fun getMember(): MemberResponseDto {
        return memberService.getMember()
    }

    override suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any> {
        return memberService.updateAge(request)
    }

    override suspend fun getCommunities(page: Int): List<CommunityByCategoryResponseDto> {
        return memberService.getCommunities(page)
    }

    override suspend fun getCommunityComments(page: Int): List<CommunityCommentDefaultResponseDto> {
        return memberService.getCommunityComments(page)
    }

    override suspend fun deleteMember(): DataResponseDto<Any> {
        return memberService.deleteMember()
    }

    override suspend fun postExistsNickname(request: NickNameRequestDto): Boolean {
        var result = true
        Log.i("postExistsNickName", "접근함")
        memberService.postExistsNickname(request).suspendOnSuccess {
            result = false
        }
        return result
    }

    override suspend fun getHearts(page: Int): List<CommunityCommentDefaultResponseDto> {
        return memberService.getHearts(page)
    }

    override suspend fun updateJoin(request: JoinUpdateRequestDto): MemberResponseDto {
        return memberService.updateJoin(request)
    }

    override suspend fun updateNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        return memberService.updateNickname(request)
    }

    override suspend fun getPerfumeComments(page: Int): List<PerfumeCommentResponseDto> {
        return memberService.getPerfumeComments(page)
    }

    override suspend fun postProfilePhoto(image: String): DataResponseDto<Any> {
        return memberService.postProfilePhoto(image)
    }

    override suspend fun deleteProfilePhoto(): DataResponseDto<Any> {
        return memberService.deleteProfilePhoto()
    }

    override suspend fun updateSex(request: SexRequestDto): DataResponseDto<Any> {
        return memberService.updateSex(request)
    }
}