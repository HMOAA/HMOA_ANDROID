package com.hmoa.core_repository.Search

import com.hmoa.core_datastore.Search.SearchDataStore
import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandSearchResponseDto
import com.hmoa.core_model.response.BrandStoryDefaultResponseDto
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.NoteDefaultResponseDto
import com.hmoa.core_model.response.PerfumeNameSearchResponseDto
import com.hmoa.core_model.response.PerfumeSearchResponseDto
import com.hmoa.core_model.response.PerfumerDefaultResponseDto
import com.hmoa.core_model.response.TermDefaultResponseDto

class SearchRepositoryImpl(
    private val searchDataStore: SearchDataStore
) : SearchRepository {

    override fun getBrand(searchWord: String): BrandSearchResponseDto {
        return searchDataStore.getBrand(searchWord)
    }

    override fun getBrandAll(consonant: Int): List<BrandDefaultResponseDto> {
        return searchDataStore.getBrandAll(consonant)
    }

    override fun getBrandStory(page: Int, searchWord: String): List<BrandStoryDefaultResponseDto> {
        return searchDataStore.getBrandStory(page, searchWord)
    }

    override fun getCommunity(page: Int, searchWord: String): List<CommunityByCategoryResponseDto> {
        return searchDataStore.getCommunity(page, searchWord)
    }

    override fun getCommunityCategory(
        category: String,
        page: Int,
        searchWord: String
    ): List<CommunityByCategoryResponseDto> {
        return searchDataStore.getCommunityCategory(category, page, searchWord)
    }

    override fun getNote(page: Int, searchWord: String): List<NoteDefaultResponseDto> {
        return searchDataStore.getNote(page, searchWord)
    }

    override fun getPerfume(page: Int, searchWord: String): List<PerfumeSearchResponseDto> {
        return searchDataStore.getPerfume(page, searchWord)
    }

    override fun getPerfumeName(page: Int, searchWord: String): List<PerfumeNameSearchResponseDto> {
        return searchDataStore.getPerfumeName(page, searchWord)
    }

    override fun getPerfumer(page: Int, searchWord: String): List<PerfumerDefaultResponseDto> {
        return searchDataStore.getPerfumer(page, searchWord)
    }

    override fun getTerm(page: Int, searchWord: String): List<TermDefaultResponseDto> {
        return searchDataStore.getTerm(page, searchWord)
    }
}