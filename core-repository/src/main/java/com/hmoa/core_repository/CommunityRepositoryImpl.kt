package com.hmoa.core_repository

import com.hmoa.core_datastore.Community.CommunityDataStore
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import java.io.File
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(private val communityDataStore: CommunityDataStore) :
    com.hmoa.core_domain.repository.CommunityRepository {
    override suspend fun getCommunity(communityId: Int): CommunityDefaultResponseDto {
        return communityDataStore.getCommunity(communityId)
    }

    override suspend fun postCommunityUpdate(
        images: Array<File>,
        deleteCommunityPhotoIds: Array<Int>,
        title: String,
        content: String,
        communityId: Int
    ): CommunityDefaultResponseDto {
        return communityDataStore.postCommunityUpdate(images, deleteCommunityPhotoIds, title, content, communityId)
    }

    override suspend fun deleteCommunity(communityId: Int): DataResponseDto<Nothing> {
        return communityDataStore.deleteCommunity(communityId)
    }

    override suspend fun getCommunityByCategory(category: Category, page: String): CommunityByCategoryResponseDto {
        return communityDataStore.getCommunityByCategory(category, page)
    }

    override suspend fun getCommunitiesHome(): List<CommunityByCategoryResponseDto> {
        return communityDataStore.getCommunitiesHome()
    }

    override suspend fun postCommunitySave(
        images: Array<File>,
        category: Category,
        title: String,
        content: String
    ): CommunityDefaultResponseDto {
        return communityDataStore.postCommunitySave(images, category, title, content)
    }
}