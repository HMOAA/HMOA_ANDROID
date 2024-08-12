package com.hyangmoa.core_datastore.BrandHPedia

import com.hyangmoa.core_model.response.DataResponseDto

interface BrandHPediaDataStore {
    suspend fun getBrandStoryAll(pageNum : Int) : DataResponseDto<Any>
    suspend fun getBrandStory(brandStoryId : Int) : DataResponseDto<Any>
    suspend fun deleteBrandStory(brandStoryId : Int) : DataResponseDto<Any>
    suspend fun updateBrandStory(brandStoryId : Int, content : String) : DataResponseDto<Any>
    suspend fun postBrandStory(brandStorySubtitle : String, brandStoryTitle : String, content : String) : DataResponseDto<Any>
    suspend fun postTestSave() : DataResponseDto<Any> //Test
}