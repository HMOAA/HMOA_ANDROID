package com.hmoa.core_datastore.BrandHPedia

import com.hmoa.core_model.response.DataResponseDto

class BrandHPediaDataStoreImpl : BrandHPediaDataStore {
    override fun getBrandStoryALl(pageNum: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun getBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun deleteBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun updateBrandStory(brandStoryId: Int, content: String): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postBrandStory(
        brandStorySubtitle: String,
        brandStoryTitle: String,
        content: String
    ): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postTestSave(): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }
}