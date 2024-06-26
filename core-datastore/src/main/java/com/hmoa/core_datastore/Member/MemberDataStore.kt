package com.hmoa.core_datastore.Member

import ResultResponse
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import java.io.File

interface MemberDataStore {
    suspend fun getMember(): ResultResponse<MemberResponseDto>
    suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any>
    suspend fun getCommunities(page: Int): ResultResponse<List<CommunityByCategoryResponseDto>>
    suspend fun getCommunityComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>>
    suspend fun getCommunityFavoriteComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>>
    suspend fun deleteMember(): DataResponseDto<Any>
    suspend fun postExistsNickname(request: NickNameRequestDto): ResultResponse<Boolean>
    suspend fun updateJoin(request: JoinUpdateRequestDto): ResultResponse<MemberResponseDto>
    suspend fun updateNickname(request: NickNameRequestDto): ResultResponse<DataResponseDto<Any>>
    suspend fun getPerfumeComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>>
    suspend fun getPerfumeFavoriteComments(page: Int): ResultResponse<List<CommunityCommentDefaultResponseDto>>
    suspend fun postProfilePhoto(image: File): ResultResponse<DataResponseDto<Any>>
    suspend fun deleteProfilePhoto(): DataResponseDto<Any>
    suspend fun updateSex(request: SexRequestDto): DataResponseDto<Any>
}