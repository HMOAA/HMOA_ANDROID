package com.hyangmoa.core_domain.repository

import ResultResponse
import com.hyangmoa.core_model.response.CommunityByCategoryResponseDto
import com.hyangmoa.core_model.response.CommunityDefaultResponseDto
import com.hyangmoa.core_model.response.CommunityWithCursorResponseDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.PagingData
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
    suspend fun getCommunityByCategory(category: String, cursor: Int): CommunityWithCursorResponseDto
    suspend fun getCommunitiesHome(): ResultResponse<List<CommunityByCategoryResponseDto>>
    suspend fun postCommunitySave(
        images: Array<File>,
        category: String,
        title: String,
        content: String
    ): ResultResponse<CommunityDefaultResponseDto>
    suspend fun getMyCommunitiesByHeart(cursor : Int) : ResultResponse<PagingData<CommunityByCategoryResponseDto>>
    suspend fun getMyCommunities(cursor : Int) : ResultResponse<PagingData<CommunityByCategoryResponseDto>>

}