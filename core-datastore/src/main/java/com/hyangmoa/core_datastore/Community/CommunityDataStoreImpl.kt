package com.hyangmoa.core_datastore.Community

import ResultResponse
import com.hyangmoa.core_datastore.Mapper.transformMultipartBody
import com.hyangmoa.core_datastore.Mapper.transformRequestBody
import com.hyangmoa.core_datastore.Mapper.transformToMultipartBody
import com.hyangmoa.core_model.data.ErrorMessage
import com.hyangmoa.core_model.response.*
import com.hyangmoa.core_network.service.CommunityService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class CommunityDataStoreImpl @Inject constructor(private val communityService: CommunityService) :
    CommunityDataStore {
    override suspend fun getCommunity(communityId: Int): ResultResponse<CommunityDefaultResponseDto> {
        val result = ResultResponse<CommunityDefaultResponseDto>()
        communityService.getCommunity(communityId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
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
        communityService.postCommunityUpdate(
            images.transformToMultipartBody(),
            deleteCommunityPhotoIds.transformRequestBody(),
            title.transformRequestBody(),
            content.transformRequestBody(),
            communityId
        ).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun deleteCommunity(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        val result = ResultResponse<DataResponseDto<Nothing>>()
        communityService.deleteCommunity(communityId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun putCommunityLike(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        val result = ResultResponse<DataResponseDto<Nothing>>()
        communityService.putCommunityLike(communityId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun deleteCommunityLike(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        val result = ResultResponse<DataResponseDto<Nothing>>()
        communityService.deleteCommunityLike(communityId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun getCommunityByCategory(
        category: String,
        cursor: Int
    ): CommunityWithCursorResponseDto {
        return communityService.getCommunityByCategory(category, cursor)
    }

    override suspend fun getCommunitiesHome(): ResultResponse<List<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<List<CommunityByCategoryResponseDto>>()
        communityService.getCommunitiesHome().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
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
        communityService.postCommunitySave(
            images.transformToMultipartBody(),
            category.transformMultipartBody("category"),
            title.transformMultipartBody("title"),
            content.transformMultipartBody("content")
        ).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun getMyCommunitiesByHeart(cursor: Int): ResultResponse<PagingData<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<PagingData<CommunityByCategoryResponseDto>>()
        communityService.getMyCommunitiesByHeart(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }

    override suspend fun getMyCommunities(cursor: Int): ResultResponse<PagingData<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<PagingData<CommunityByCategoryResponseDto>>()
        communityService.getMyCommunities(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            val errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
            result.errorMessage = errorMessage
        }
        return result
    }
}