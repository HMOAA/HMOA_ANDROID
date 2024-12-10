package com.hmoa.core_database.lrucache

import com.hmoa.core_model.response.PerfumeRecommendsResponseDto

interface PerfumeRecommendCacheManager {
    fun saveNoteSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto)
    fun getNoteSortedPerfumeRecommendsResult(): PerfumeRecommendsResponseDto?

    fun savePriceSortedPerfumeRecommendsResult(dto: PerfumeRecommendsResponseDto)
    fun getPriceSortedPerfumeRecommendsResult(): PerfumeRecommendsResponseDto?
}