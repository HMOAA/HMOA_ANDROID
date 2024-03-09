package com.hmoa.core_repository

import com.hmoa.core_datastore.Member.MemberDataStore
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.*
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberDataStore: MemberDataStore
) : com.hmoa.core_domain.repository.MemberRepository {

    override suspend fun getMember(): MemberResponseDto {
        return memberDataStore.getMember()
    }

    override suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any> {
        return memberDataStore.updateAge(request)
    }

    override suspend fun getCommunities(page: Int): List<CommunityByCategoryResponseDto> {
        return memberDataStore.getCommunities(page)
    }

    override suspend fun getCommunityComments(page: Int): List<CommunityCommentDefaultResponseDto> {
        return memberDataStore.getCommunityComments(page)
    }

    override suspend fun deleteMember(): DataResponseDto<Any> {
        return memberDataStore.deleteMember()
    }

    override suspend fun postExistsNickname(request: NickNameRequestDto): Boolean {
        return memberDataStore.postExistsNickname(request)
    }

    override suspend fun getHearts(page: Int): List<CommunityCommentDefaultResponseDto> {
        return memberDataStore.getHearts(page)
    }

    override suspend fun updateJoin(request: JoinUpdateRequestDto): MemberResponseDto {
        return memberDataStore.updateJoin(request)
    }

    override suspend fun updateNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        return memberDataStore.updateNickname(request)
    }

    override suspend fun getPerfumeComments(page: Int): List<PerfumeCommentResponseDto> {
        return memberDataStore.getPerfumeComments(page)
    }

    override suspend fun postProfilePhoto(image: String): DataResponseDto<Any> {
        return memberDataStore.postProfilePhoto(image)
    }

    override suspend fun deleteProfilePhoto(): DataResponseDto<Any> {
        return memberDataStore.deleteProfilePhoto()
    }

    override suspend fun updateSex(request: SexRequestDto): DataResponseDto<Any> {
        return memberDataStore.updateSex(request)
    }
}