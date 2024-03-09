package com.hmoa.core_domain.repository

import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import java.io.File

interface CommunityRepository {
    suspend fun getCommunity(communityId: Int): CommunityDefaultResponseDto
    suspend fun postCommunityUpdate(
        images: Array<File>,
        deleteCommunityPhotoIds: Array<Int>,
        title: String,
        content: String,
        communityId: Int
    ): CommunityDefaultResponseDto

    suspend fun deleteCommunity(communityId: Int): DataResponseDto<Nothing>
    suspend fun getCommunityByCategory(category: Category, page: String): CommunityByCategoryResponseDto
    suspend fun getCommunitiesHome(): List<CommunityByCategoryResponseDto>
    suspend fun postCommunitySave(
        images: Array<File>,
        category: Category,
        title: String,
        content: String
    ): CommunityDefaultResponseDto
}