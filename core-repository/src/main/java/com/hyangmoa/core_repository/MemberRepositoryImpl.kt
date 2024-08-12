package com.hyangmoa.core_repository

import ResultResponse
import com.hyangmoa.core_datastore.Member.MemberDataStore
import com.hyangmoa.core_model.request.AgeRequestDto
import com.hyangmoa.core_model.request.JoinUpdateRequestDto
import com.hyangmoa.core_model.request.NickNameRequestDto
import com.hyangmoa.core_model.request.SexRequestDto
import com.hyangmoa.core_model.response.CommunityByCategoryResponseDto
import com.hyangmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.MemberResponseDto
import java.io.File
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberDataStore: MemberDataStore
) : com.hyangmoa.core_domain.repository.MemberRepository {

    override suspend fun getMember(): ResultResponse<MemberResponseDto> {
        return memberDataStore.getMember()
    }

    override suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any> {
        return memberDataStore.updateAge(request)
    }

    override suspend fun getCommunities(page: Int): ResultResponse<List<CommunityByCategoryResponseDto>> {
        return memberDataStore.getCommunities(page)
    }

    override suspend fun getCommunityComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        return memberDataStore.getCommunityComments(page)
    }

    override suspend fun deleteMember(): DataResponseDto<Any> {
        return memberDataStore.deleteMember()
    }

    override suspend fun postExistsNickname(request: NickNameRequestDto): ResultResponse<Boolean> {
        return memberDataStore.postExistsNickname(request)
    }

    override suspend fun getCommunityFavoriteComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        return memberDataStore.getCommunityFavoriteComments(page)
    }

    override suspend fun getPerfumeFavoriteComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        return memberDataStore.getPerfumeFavoriteComments(page)
    }

    override suspend fun updateJoin(request: JoinUpdateRequestDto): ResultResponse<MemberResponseDto> {
        return memberDataStore.updateJoin(request)
    }

    override suspend fun updateNickname(request: NickNameRequestDto): ResultResponse<DataResponseDto<Any>> {
        return memberDataStore.updateNickname(request)
    }

    override suspend fun getPerfumeComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>> {
        return memberDataStore.getPerfumeComments(page)
    }

    override suspend fun postProfilePhoto(image: File): ResultResponse<DataResponseDto<Any>> {
        return memberDataStore.postProfilePhoto(image)
    }

    override suspend fun deleteProfilePhoto(): DataResponseDto<Any> {
        return memberDataStore.deleteProfilePhoto()
    }

    override suspend fun updateSex(request: SexRequestDto): DataResponseDto<Any> {
        return memberDataStore.updateSex(request)
    }
}