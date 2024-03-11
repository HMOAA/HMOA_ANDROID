package com.hmoa.core_datastore.Member

import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.request.NickNameRequestDto
import com.hmoa.core_model.request.SexRequestDto
import com.hmoa.core_model.response.*

interface MemberDataStore {
    suspend fun getMember(): MemberResponseDto
    suspend fun updateAge(request: AgeRequestDto): DataResponseDto<Any>
    suspend fun getCommunities(page: Int): List<CommunityByCategoryResponseDto>
    suspend fun getCommunityComments(page: Int): List<CommunityCommentDefaultResponseDto>
    suspend fun getCommunityFavoriteComments(page : Int) : List<CommunityCommentDefaultResponseDto>
    suspend fun deleteMember(): DataResponseDto<Any>
    suspend fun postExistsNickname(request: NickNameRequestDto): Boolean
    suspend fun updateJoin(request: JoinUpdateRequestDto): MemberResponseDto
    suspend fun updateNickname(request: NickNameRequestDto): DataResponseDto<Any>
    suspend fun getPerfumeComments(page: Int): List<CommunityCommentDefaultResponseDto>
    suspend fun getPerfumeFavoriteComments(page : Int) : List<CommunityCommentDefaultResponseDto>
    suspend fun postProfilePhoto(image: String): DataResponseDto<Any>
    suspend fun deleteProfilePhoto(): DataResponseDto<Any>
    suspend fun updateSex(request: SexRequestDto): DataResponseDto<Any>
}