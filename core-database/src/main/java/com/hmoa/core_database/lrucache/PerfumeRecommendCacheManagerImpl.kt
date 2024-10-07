package com.hmoa.core_database.lrucache

import android.util.LruCache
import com.hmoa.core_model.response.PerfumeRecommendsResponseDto
import javax.inject.Inject

class PerfumeRecommendCacheManagerImpl @Inject constructor() : PerfumeRecommendCacheManager {
    // 클래스 멤버로 LruCache 선언
    private val cacheSize = 4 * 1024 * 1024 // 4MB...적당한가??
    private val perfumeRecommendsResultCache = LruCache<String, PerfumeRecommendsResponseDto>(cacheSize)
    override fun saveNoteSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto) {
        perfumeRecommendsResultCache.put(CacheKey.NoteSortedPerfumeRecommendsResult.name, dto)
    }

    override fun getNoteSortedPerfumeRecommendsResult(): PerfumeRecommendsResponseDto? {
        return perfumeRecommendsResultCache.get(CacheKey.NoteSortedPerfumeRecommendsResult.name)
    }

    override fun savePriceSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto) {
        perfumeRecommendsResultCache.put(CacheKey.PriceSortedPerfumeRecommendsResult.name, dto)
    }

    override fun getPriceSortedPerfumeRecommendsResult(): PerfumeRecommendsResponseDto? {
        return perfumeRecommendsResultCache.get(CacheKey.PriceSortedPerfumeRecommendsResult.name)
    }
}