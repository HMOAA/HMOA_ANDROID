package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Member.MemberDataStore
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberDataStore: MemberDataStore
) : com.hmoa.core_domain.repository.MemberRepository {

    override suspend fun getMember(): ResultResponse<MemberResponseDto> {
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

    override suspend fun getCommunityFavoriteComments(page: Int): List<CommunityCommentDefaultResponseDto> {
        return memberDataStore.getCommunityFavoriteComments(page)
    }

    override suspend fun getPerfumeFavoriteComments(page: Int): List<CommunityCommentDefaultResponseDto> {
        return memberDataStore.getPerfumeFavoriteComments(page)
    }

    override suspend fun updateJoin(request: JoinUpdateRequestDto): ResultResponse<MemberResponseDto> {
        return memberDataStore.updateJoin(request)
    }

    override suspend fun updateNickname(request: NickNameRequestDto): DataResponseDto<Any> {
        return memberDataStore.updateNickname(request)
    }

    override suspend fun getPerfumeComments(page: Int): List<CommunityCommentDefaultResponseDto> {
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