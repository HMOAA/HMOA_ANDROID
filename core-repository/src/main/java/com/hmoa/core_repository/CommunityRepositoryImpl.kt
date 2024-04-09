package com.hmoa.core_repository

import ResultResponse
import android.util.Log
import com.hmoa.core_datastore.Community.CommunityDataStore
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import java.io.File
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(private val communityDataStore: CommunityDataStore) :
    com.hmoa.core_domain.repository.CommunityRepository {
    override suspend fun getCommunity(communityId: Int): ResultResponse<CommunityDefaultResponseDto> {
        return communityDataStore.getCommunity(communityId)
    }

    override suspend fun postCommunityUpdate(
        images: Array<File>,
        deleteCommunityPhotoIds: Array<Int>,
        title: String,
        content: String,
        communityId: Int
    ): ResultResponse<CommunityDefaultResponseDto> {
        return communityDataStore.postCommunityUpdate(images, deleteCommunityPhotoIds, title, content, communityId)
    }

    override suspend fun deleteCommunity(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        return communityDataStore.deleteCommunity(communityId)
    }

    override suspend fun putCommunityLike(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        return communityDataStore.putCommunityLike(communityId)
    }

    override suspend fun deleteCommunityLike(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        return communityDataStore.deleteCommunityLike(communityId)
    }

    override suspend fun getCommunityByCategory(
        category: String,
        page: Int
    ): ResultResponse<List<CommunityByCategoryResponseDto>> {
        return communityDataStore.getCommunityByCategory(category, page)
    }

    override suspend fun getCommunitiesHome(): ResultResponse<List<CommunityByCategoryResponseDto>> {
        return communityDataStore.getCommunitiesHome()
    }

    override suspend fun postCommunitySave(
        images: Array<File>,
        category: String,
        title: String,
        content: String
    ): ResultResponse<CommunityDefaultResponseDto> {
        return communityDataStore.postCommunitySave(images, category, title, content)
    }
}