package com.hmoa.core_datastore.Community

import ResultResponse
import com.hmoa.core_model.Category
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.CommunityCommentWithLikedResponseDto
import com.hmoa.core_model.response.CommunityDefaultResponseDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.CommunityService
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import java.io.File
import javax.inject.Inject

class CommunityDataStoreImpl @Inject constructor(private val communityService: CommunityService) :
    CommunityDataStore {
    override suspend fun getCommunity(communityId: Int): ResultResponse<CommunityDefaultResponseDto> {
        val result = ResultResponse<CommunityDefaultResponseDto>()
        communityService.getCommunity(communityId).suspendMapSuccess{
            result.data = this
        }.suspendOnError {
            result.errorCode = response.code()
            result.errorMessage = response.message()
        }
        return result
    }

    override suspend fun postCommunityUpdate(
        images: Array<File>,
        deleteCommunityPhotoIds: Array<Int>,
        title: String,
        content: String,
        communityId: Int
    ): ResultResponse<CommunityDefaultResponseDto> {
        val result = ResultResponse<CommunityDefaultResponseDto>()
        communityService.postCommunityUpdate(images, deleteCommunityPhotoIds, title, content, communityId).suspendMapSuccess{
            result.data = this
        }.suspendOnError {
            result.errorCode = response.code()
            result.errorMessage = response.message()
        }
        return result
    }

    override suspend fun deleteCommunity(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        val result = ResultResponse<DataResponseDto<Nothing>>()
        communityService.deleteCommunity(communityId).suspendMapSuccess{
            result.data = this
        }.suspendOnError {
            result.errorCode = response.code()
            result.errorMessage = response.message()
        }
        return result
    }

    override suspend fun putCommunityLike(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        val result = ResultResponse<DataResponseDto<Nothing>>()
        communityService.putCommunityLike(communityId).suspendMapSuccess{
            result.data = this
        }.suspendOnError {
            result.errorCode = response.code()
            result.errorMessage = response.message()
        }
        return result
    }

    override suspend fun deleteCommunityLike(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        val result = ResultResponse<DataResponseDto<Nothing>>()
        communityService.deleteCommunity(communityId).suspendMapSuccess{
            result.data = this
        }.suspendOnError {
            result.errorCode = response.code()
            result.errorMessage = response.message()
        }
        return result
    }

    override suspend fun getCommunityByCategory(
        category: String,
        page: Int
    ): ResultResponse<List<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<List<CommunityByCategoryResponseDto>>()
        communityService.getCommunityByCategory(category, page).suspendMapSuccess{
            result.data = this
        }.suspendOnError {
            result.errorCode = response.code()
            result.errorMessage = response.message()
        }
        return result
    }

    override suspend fun getCommunitiesHome(): ResultResponse<List<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<List<CommunityByCategoryResponseDto>>()
        communityService.getCommunitiesHome().suspendMapSuccess{
            result.data = this
        }.suspendOnError {
            result.errorCode = response.code()
            result.errorMessage = response.message()
        }
        return result
    }

    override suspend fun postCommunitySave(
        images: Array<File>,
        category: String,
        title: String,
        content: String
    ): ResultResponse<CommunityDefaultResponseDto> {
        val result = ResultResponse<CommunityDefaultResponseDto>()
        communityService.postCommunitySave(images, category, title, content).suspendMapSuccess{
            result.data = this
        }.suspendOnError {
            result.errorCode = response.code()
            result.errorMessage = response.message()
        }
        return result
    }
}