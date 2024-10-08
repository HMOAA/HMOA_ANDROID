package com.hmoa.core_network.service

import com.hmoa.core_model.response.*
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/search/brand")
    suspend fun getBrand(@Query("searchWord") searchWord: String): ApiResponse<List<BrandSearchResponseDto>>

    @GET("/search/brandAll")
    suspend fun getBrandAll(@Query("consonant") consonant: Int): ApiResponse<List<BrandDefaultResponseDto>>

    @GET("/search/brandStory")
    suspend fun getBrandStory(
        @Query("page") page: Int,
        @Query("searchWord") searchWord: String
    ): List<BrandStoryDefaultResponseDto>

    @GET("/search/community")
    suspend fun getCommunity(
        @Query("page") page: Int,
        @Query("seachWord") searchWord: String
    ): ApiResponse<List<CommunityByCategoryResponseDto>>

    @GET("/search/community/category")
    suspend fun getCommunityCategory(
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("searchWord") searchWord: String
    ): List<CommunityByCategoryResponseDto>

    @GET("/search/note")
    suspend fun getNote(
        @Query("page") page: Int,
        @Query("seachWord") searchWord: String
    ): List<NoteDefaultResponseDto>

    @GET("/search/perfume")
    suspend fun getPerfume(
        @Query("page") page: Int,
        @Query("searchWord") searchWord: String
    ): ApiResponse<List<PerfumeSearchResponseDto>>

    @GET("/search/perfumeName")
    suspend fun getPerfumeName(
        @Query("page") page: Int,
        @Query("searchWord") searchWord: String
    ): ApiResponse<List<PerfumeNameSearchResponseDto>>

    @GET("/search/perfumer")
    suspend fun getPerfumer(
        @Query("page") page: Int,
        @Query("seachWord") searchWord: String
    ): List<PerfumerDefaultResponseDto>

    @GET("/search/term")
    suspend fun getTerm(
        @Query("page") page: Int,
        @Query("seachWord") searchWord: String
    ): List<TermDefaultResponseDto>
}