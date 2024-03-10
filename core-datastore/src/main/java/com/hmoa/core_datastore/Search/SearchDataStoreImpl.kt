package com.hmoa.core_datastore.Search

import com.hmoa.core_model.response.*
import com.hmoa.core_network.service.SearchService
import javax.inject.Inject

class SearchDataStoreImpl @Inject constructor(
    private val searchService: SearchService
) : SearchDataStore {
    override suspend fun getBrand(searchWord: String): BrandSearchResponseDto {
        return searchService.getBrand(searchWord)
    }

    override suspend fun getBrandAll(consonant: Int): List<BrandDefaultResponseDto> {
        return searchService.getBrandAll(consonant)
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
    ): List<CommunityByCategoryResponseDto> {
        return searchService.getCommunity(page, searchWord)
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

    override suspend fun getPerfume(page: Int, searchWord: String): List<PerfumeSearchResponseDto> {
        return searchService.getPerfume(page, searchWord)
    }

    override suspend fun getPerfumeName(
        page: Int,
        searchWord: String
    ): List<PerfumeNameSearchResponseDto> {
        return searchService.getPerfumeName(page, searchWord)
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