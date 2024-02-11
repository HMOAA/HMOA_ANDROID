package com.hmoa.core_datastore.Search

import com.hmoa.core_model.response.BrandDefaultResponseDto
import com.hmoa.core_model.response.BrandSearchResponseDto
import com.hmoa.core_model.response.BrandStoryDefaultResponseDto
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import com.hmoa.core_model.response.NoteDefaultResponseDto
import com.hmoa.core_model.response.PerfumeNameSearchResponseDto
import com.hmoa.core_model.response.PerfumeSearchResponseDto
import com.hmoa.core_model.response.PerfumerDefaultResponseDto
import com.hmoa.core_model.response.TermDefaultResponseDto

private class SearchDataStoreImpl : SearchDataStore {
    override fun getBrand(searchWord: String): BrandSearchResponseDto {
        TODO("Not yet implemented")
    }

    override fun getBrandAll(consonant: Int): List<BrandDefaultResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getBrandStory(page: Int, searchWord: String): List<BrandStoryDefaultResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getCommunity(page: Int, searchWord: String): List<CommunityByCategoryResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getCommunityCategory(
        category: String,
        page: Int,
        searchWord: String
    ): List<CommunityByCategoryResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getNote(page: Int, searchWord: String): List<NoteDefaultResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getPerfume(page: Int, searchWord: String): List<PerfumeSearchResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getPerfumeName(page: Int, searchWord: String): List<PerfumeNameSearchResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getPerfumer(page: Int, searchWord: String): List<PerfumerDefaultResponseDto> {
        TODO("Not yet implemented")
    }

    override fun getTerm(page: Int, searchWord: String): List<TermDefaultResponseDto> {
        TODO("Not yet implemented")
    }
}