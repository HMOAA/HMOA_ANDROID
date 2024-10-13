package com.hmoa.core_datastore.BrandHPedia

import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_network.service.BrandHPediaService
import javax.inject.Inject

class BrandHPediaDataStoreImpl @Inject constructor(
    private val brandHPediaService: BrandHPediaService
) : BrandHPediaDataStore {
    override suspend fun getBrandStoryAll(pageNum: Int): DataResponseDto<Any> {
        return brandHPediaService.getBrandStoryAll(pageNum)
    }

    override suspend fun getBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        return brandHPediaService.getBrandStory(brandStoryId)
    }

    override suspend fun deleteBrandStory(brandStoryId: Int): DataResponseDto<Any> {
        return brandHPediaService.deleteBrandStory(brandStoryId)
    }

    override suspend fun updateBrandStory(
        brandStoryId: Int,
        content: String
    ): DataResponseDto<Any> {
        return brandHPediaService.updateBrandStory(brandStoryId, content)
    }
}