package com.hmoa.core_domain.repository

import com.hmoa.core_model.response.*

interface SearchRepository {
    suspend fun getBrand(searchWord: String): BrandSearchResponseDto
    suspend fun getBrandAll(consonant: Int): List<BrandDefaultResponseDto>
    suspend fun getBrandStory(page: Int, searchWord: String): List<BrandStoryDefaultResponseDto>
    suspend fun getCommunity(page: Int, searchWord: String): List<CommunityByCategoryResponseDto>
    suspend fun getCommunityCategory(
        category: String,
        page: Int,
        searchWord: String
    ): List<CommunityByCategoryResponseDto>

    suspend fun getNote(page: Int, searchWord: String): List<NoteDefaultResponseDto>
    suspend fun getPerfume(page: Int, searchWord: String): List<PerfumeSearchResponseDto>
    suspend fun getPerfumeName(page: Int, searchWord: String): List<PerfumeNameSearchResponseDto>
    suspend fun getPerfumer(page: Int, searchWord: String): List<PerfumerDefaultResponseDto>
    suspend fun getTerm(page: Int, searchWord: String): List<TermDefaultResponseDto>
}