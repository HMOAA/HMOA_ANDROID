package com.hmoa.core_datastore.Search

import ResultResponse
import com.hmoa.core_model.response.*
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.SearchService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class SearchDataStoreImpl @Inject constructor(
    private val searchService: SearchService,
    private val authenticator: Authenticator
) : SearchDataStore {
    override suspend fun getBrand(searchWord: String): ResultResponse<List<BrandSearchResponseDto>> {
        val result = ResultResponse<List<BrandSearchResponseDto>>()
        searchService.getBrand(searchWord).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    searchService.getBrand(searchWord).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getBrandAll(consonant: Int): ResultResponse<List<BrandDefaultResponseDto>> {
        val result = ResultResponse<List<BrandDefaultResponseDto>>()
        searchService.getBrandAll(consonant).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    searchService.getBrandAll(consonant).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getBrandStory(
        page: Int,
        searchWord: String
    ): List<BrandStoryDefaultResponseDto> {
        return searchService.getBrandStory(page, searchWord)
    }

    override suspend fun getCommunity(
        page: Int,
        searchWord: String
    ): ResultResponse<List<CommunityByCategoryResponseDto>> {
        val result = ResultResponse<List<CommunityByCategoryResponseDto>>()
        searchService.getCommunity(page, searchWord).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    searchService.getCommunity(page, searchWord).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getCommunityCategory(
        category: String,
        page: Int,
        searchWord: String
    ): List<CommunityByCategoryResponseDto> {
        return getCommunityCategory(category, page, searchWord)
    }

    override suspend fun getNote(page: Int, searchWord: String): List<NoteDefaultResponseDto> {
        return searchService.getNote(page, searchWord)
    }

    override suspend fun getPerfume(page: Int, searchWord: String): ResultResponse<List<PerfumeSearchResponseDto>> {
        var result = ResultResponse<List<PerfumeSearchResponseDto>>(null)
        searchService.getPerfume(page, searchWord).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    searchService.getPerfume(page, searchWord).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getPerfumeName(
        page: Int,
        searchWord: String
    ): ResultResponse<List<PerfumeNameSearchResponseDto>> {
        var result = ResultResponse<List<PerfumeNameSearchResponseDto>>(null)
        searchService.getPerfumeName(page, searchWord).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    searchService.getPerfumeName(page, searchWord).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getPerfumer(
        page: Int,
        searchWord: String
    ): List<PerfumerDefaultResponseDto> {
        return searchService.getPerfumer(page, searchWord)
    }

    override suspend fun getTerm(page: Int, searchWord: String): List<TermDefaultResponseDto> {
        return searchService.getTerm(page, searchWord)
    }
}