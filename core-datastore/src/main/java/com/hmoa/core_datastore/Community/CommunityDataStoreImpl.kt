package com.hmoa.core_datastore.Community

import ResultResponse
import com.hmoa.core_datastore.Mapper.transformMultipartBody
import com.hmoa.core_datastore.Mapper.transformRequestBody
import com.hmoa.core_datastore.Mapper.transformToMultipartBody
import com.hmoa.core_model.response.*
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.CommunityService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import java.io.File
import javax.inject.Inject

class CommunityDataStoreImpl @Inject constructor(
    private val communityService: CommunityService,
    private val authenticator: Authenticator
) :
    CommunityDataStore {
    override suspend fun getCommunity(communityId: Int): ResultResponse<CommunityDefaultResponseDto> {
        val result = ResultResponse<CommunityDefaultResponseDto>()
        communityService.getCommunity(communityId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityService.getCommunity(communityId).suspendOnSuccess { result.data = this.data }
                }
            )
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
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityService.postCommunityUpdate(
                        images.transformToMultipartBody(),
                        deleteCommunityPhotoIds.transformRequestBody(),
                        title.transformRequestBody(),
                        content.transformRequestBody(),
                        communityId
                    ).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun deleteCommunity(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        val result = ResultResponse<DataResponseDto<Nothing>>()
        communityService.deleteCommunity(communityId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityService.deleteCommunity(communityId).suspendMapSuccess { result.data = this }
                }
            )
        }
        return result
    }

    override suspend fun putCommunityLike(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        val result = ResultResponse<DataResponseDto<Nothing>>()
        communityService.putCommunityLike(communityId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityService.putCommunityLike(communityId).suspendMapSuccess { result.data = this }
                }
            )
        }
        return result
    }

    override suspend fun deleteCommunityLike(communityId: Int): ResultResponse<DataResponseDto<Nothing>> {
        val result = ResultResponse<DataResponseDto<Nothing>>()
        communityService.deleteCommunityLike(communityId).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityService.deleteCommunityLike(communityId).suspendMapSuccess { result.data = this }
                }
            )
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
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityService.getCommunitiesHome().suspendMapSuccess { result.data = this }
                }
            )
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
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityService.postCommunitySave(
                        images.transformToMultipartBody(),
                        category.transformMultipartBody("category"),
                        title.transformMultipartBody("title"),
                        content.transformMultipartBody("content")
                    ).suspendMapSuccess { result.data = this }
                }
            )
        }
        return result
    }

    override suspend fun getMyCommunitiesByHeart(cursor: Int): ResultResponse<PagingData<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<PagingData<CommunityByCategoryResponseDto>>()
        communityService.getMyCommunitiesByHeart(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityService.getMyCommunitiesByHeart(cursor).suspendMapSuccess { result.data = this }
                }
            )
        }
        return result
    }

    override suspend fun getMyCommunities(cursor: Int): ResultResponse<PagingData<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<PagingData<CommunityByCategoryResponseDto>>()
        communityService.getMyCommunities(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    communityService.getMyCommunities(cursor).suspendMapSuccess { result.data = this }
                }
            )
        }
        return result
    }
}