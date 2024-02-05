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

interface SearchDataStore {
    fun getBrand(searchWord : String) : BrandSearchResponseDto
    fun getBrandAll(consonant : Int) : List<BrandDefaultResponseDto>
    fun getBrandStory(page : Int, searchWord : String) : List<BrandStoryDefaultResponseDto>
    fun getCommunity(page : Int, searchWord : String) : List<CommunityByCategoryResponseDto>
    fun getCommunityCategory(category : String, page : Int, searchWord : String) : List<CommunityByCategoryResponseDto>
    fun getNote(page : Int, searchWord : String) : List<NoteDefaultResponseDto>
    fun getPerfume(page : Int, searchWord : String) : List<PerfumeSearchResponseDto>
    fun getPerfumeName(page : Int, searchWord : String) : List<PerfumeNameSearchResponseDto>
    fun getPerfumer(page : Int, searchWord : String) : List<PerfumerDefaultResponseDto>
    fun getTerm(page : Int, searchWord : String) : List<TermDefaultResponseDto>
}