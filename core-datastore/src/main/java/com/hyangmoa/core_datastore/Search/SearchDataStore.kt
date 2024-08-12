package com.hyangmoa.core_datastore.Search

import ResultResponse
import com.hyangmoa.core_model.response.*

interface SearchDataStore {
    suspend fun getBrand(searchWord: String): ResultResponse<List<BrandSearchResponseDto>>
    suspend fun getBrandAll(consonant: Int): ResultResponse<List<BrandDefaultResponseDto>>
    suspend fun getBrandStory(page: Int, searchWord: String): List<BrandStoryDefaultResponseDto>
    suspend fun getCommunity(page: Int, searchWord: String): ResultResponse<List<CommunityByCategoryResponseDto>>
    suspend fun getCommunityCategory(
        category: String,
        page: Int,
        searchWord: String
    ): List<CommunityByCategoryResponseDto>

    suspend fun getNote(page: Int, searchWord: String): List<NoteDefaultResponseDto>
    suspend fun getPerfume(page: Int, searchWord: String): ResultResponse<List<PerfumeSearchResponseDto>>
    suspend fun getPerfumeName(page: Int, searchWord: String): ResultResponse<List<PerfumeNameSearchResponseDto>>
    suspend fun getPerfumer(page: Int, searchWord: String): List<PerfumerDefaultResponseDto>
    suspend fun getTerm(page: Int, searchWord: String): List<TermDefaultResponseDto>
}