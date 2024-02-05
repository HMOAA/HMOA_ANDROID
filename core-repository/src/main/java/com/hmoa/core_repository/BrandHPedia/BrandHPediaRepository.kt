package com.hmoa.core_repository.BrandHPedia

import com.hmoa.core_model.response.DataResponseDto

interface BrandHPediaRepository {
    fun getBrandStoryAll(pageNum : Int) : DataResponseDto<Any>
    fun getBrandStory(brandStoryId : Int) : DataResponseDto<Any>
    fun deleteBrandStory(brandStoryId : Int) : DataResponseDto<Any>
    fun updateBrandStory(brandStoryId : Int, content : String) : DataResponseDto<Any>
    fun postBrandStory(brandStorySubtitle : String, brandStoryTitle : String, content : String) : DataResponseDto<Any>
    fun postTestSave() : DataResponseDto<Any> //Test
}