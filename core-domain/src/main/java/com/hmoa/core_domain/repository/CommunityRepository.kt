package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import java.io.File

interface CommunityRepository {
    suspend fun getCommunity(communityId: Int): ResultResponse<CommunityDefaultResponseDto>
    suspend fun postCommunityUpdate(
        images: Array<File>,
        deleteCommunityPhotoIds: Array<Int>,
        title: String,
        content: String,
        communityId: Int
    ): ResultResponse<CommunityDefaultResponseDto>

    suspend fun deleteCommunity(communityId: Int): ResultResponse<DataResponseDto<Nothing>>
    suspend fun putCommunityLike(communityId : Int) : ResultResponse<DataResponseDto<Nothing>>
    suspend fun deleteCommunityLike(communityId: Int) : ResultResponse<DataResponseDto<Nothing>>
    suspend fun getCommunityByCategory(category: String, page: Int): ResultResponse<List<CommunityByCategoryResponseDto>>
    suspend fun getCommunitiesHome(): ResultResponse<List<CommunityByCategoryResponseDto>>
    suspend fun postCommunitySave(
        images: Array<File>,
        category: String,
        title: String,
        content: String
    ): ResultResponse<CommunityDefaultResponseDto>
}