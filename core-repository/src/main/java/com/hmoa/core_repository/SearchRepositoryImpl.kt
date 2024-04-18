package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Search.SearchDataStore
import com.hmoa.core_model.response.*
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataStore: SearchDataStore
) : com.hmoa.core_domain.repository.SearchRepository {

    override suspend fun getBrand(searchWord: String): ResultResponse<List<BrandSearchResponseDto>> {
        return searchDataStore.getBrand(searchWord)
    }

    override suspend fun getBrandAll(consonant: Int): ResultResponse<List<BrandDefaultResponseDto>> {
        return searchDataStore.getBrandAll(consonant)
    }

    override suspend fun getBrandStory(page: Int, searchWord: String): List<BrandStoryDefaultResponseDto> {
        return searchDataStore.getBrandStory(page, searchWord)
    }

    override suspend fun getCommunity(page: Int, searchWord: String): ResultResponse<List<CommunityByCategoryResponseDto>> {
        return searchDataStore.getCommunity(page, searchWord)
    }

    override suspend fun getCommunityCategory(
        category: String,
        page: Int,
        searchWord: String
    ): List<CommunityByCategoryResponseDto> {
        return searchDataStore.getCommunityCategory(category, page, searchWord)
    }

    override suspend fun getNote(page: Int, searchWord: String): List<NoteDefaultResponseDto> {
        return searchDataStore.getNote(page, searchWord)
    }

    override suspend fun getPerfume(page: Int, searchWord: String): List<PerfumeSearchResponseDto> {
        return searchDataStore.getPerfume(page, searchWord)
    }

    override suspend fun getPerfumeName(page: Int, searchWord: String): List<PerfumeNameSearchResponseDto> {
        return searchDataStore.getPerfumeName(page, searchWord)
    }

    override suspend fun getPerfumer(page: Int, searchWord: String): List<PerfumerDefaultResponseDto> {
        return searchDataStore.getPerfumer(page, searchWord)
    }

    override suspend fun getTerm(page: Int, searchWord: String): List<TermDefaultResponseDto> {
        return searchDataStore.getTerm(page, searchWord)
    }
}