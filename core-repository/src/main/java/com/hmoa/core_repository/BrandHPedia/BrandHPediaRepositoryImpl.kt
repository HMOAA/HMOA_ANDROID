package com.hmoa.core_repository.BrandHPedia

import com.hmoa.core_datastore.BrandHPedia.BrandHPediaDataStore
import com.hmoa.core_model.response.DataResponseDto

class BrandHPediaRepositoryImpl(
    private val brandHPediaDataStore: BrandHPediaDataStore
) : BrandHPediaRepository {

    override suspend fun getBrandStoryAll(pageNum: Int): DataResponseDto<Any> {
        return brandHPediaDataStore.getBrandStoryAll(pageNum)
    }

    override suspend fun getBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        return brandHPediaDataStore.getBrandStory(brandStoryId)
    }

    override suspend fun deleteBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        return brandHPediaDataStore.deleteBrandStory(brandStoryId)
    }

    override suspend fun updateBrandStory(brandStoryId: Int, content: String): DataResponseDto<Any> {
        return brandHPediaDataStore.updateBrandStory(brandStoryId, content)
    }

    override suspend fun postBrandStory(
        brandStorySubtitle: String,
        brandStoryTitle: String,
        content: String
    ): DataResponseDto<Any> {
        return brandHPediaDataStore.postBrandStory(brandStorySubtitle, brandStoryTitle, content)
    }

    override suspend fun postTestSave(): DataResponseDto<Any> {
        return brandHPediaDataStore.postTestSave()
    }
}