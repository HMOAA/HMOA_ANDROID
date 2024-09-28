package com.hmoa.core_database.lrucache

import com.hmoa.core_model.response.PerfumeRecommendsResponseDto

interface PerfumeRecommendCacheManager {
    fun savePerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto)
    fun getPerfumeRecommendsResult(): PerfumeRecommendsResponseDto?
}