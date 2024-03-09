package com.hmoa.core_datastore.Community

import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.CommunityService
import java.io.File
import javax.inject.Inject

class CommunityDataStoreImpl @Inject constructor(private val communityService: CommunityService) :
    CommunityDataStore {
    override suspend fun getCommunity(communityId: Int): CommunityDefaultResponseDto {
        return communityService.getCommunity(communityId)
    }

    override suspend fun postCommunityUpdate(
        images: Array<File>,
        deleteCommunityPhotoIds: Array<Int>,
        title: String,
        content: String,
        communityId: Int
    ): CommunityDefaultResponseDto {
        return communityService.postCommunityUpdate(images, deleteCommunityPhotoIds, title, content, communityId)
    }

    override suspend fun deleteCommunity(communityId: Int): DataResponseDto<Nothing> {
        return communityService.deleteCommunity(communityId)
    }

    override suspend fun getCommunityByCategory(category: Category, page: String): CommunityByCategoryResponseDto {
        return communityService.getCommunityByCategory(category, page)
    }

    override suspend fun getCommunitiesHome(): List<CommunityByCategoryResponseDto> {
        return communityService.getCommunitiesHome()
    }

    override suspend fun postCommunitySave(
        images: Array<File>,
        category: Category,
        title: String,
        content: String
    ): CommunityDefaultResponseDto {
        return communityService.postCommunitySave(images, category, title, content)
    }

}