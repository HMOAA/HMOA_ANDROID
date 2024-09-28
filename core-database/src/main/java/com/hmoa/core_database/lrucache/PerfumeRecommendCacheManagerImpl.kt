package com.hmoa.core_database.lrucache

import android.util.LruCache
import com.hmoa.core_model.response.PerfumeRecommendsResponseDto
import javax.inject.Inject

class PerfumeRecommendCacheManagerImpl @Inject constructor() : PerfumeRecommendCacheManager {
    // 클래스 멤버로 LruCache 선언
    private val cacheSize = 4 * 1024 * 1024 // 4MB
    private val perfumeRecommendsResultCache = LruCache<String, PerfumeRecommendsResponseDto>(cacheSize)

    // 데이터를 캐시에 저장
    override fun savePerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto) {
        perfumeRecommendsResultCache.put(CacheKey.PerfumeRecommendsResult.name, dto)
    }

    // 캐시에서 데이터 가져오기
    override fun getPerfumeRecommendsResult(): PerfumeRecommendsResponseDto? {
        return perfumeRecommendsResultCache.get(CacheKey.PerfumeRecommendsResult.name)
    }
}