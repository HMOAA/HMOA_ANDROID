package com.hmoa.core_network

import com.hmoa.core_model.response.PerfumeDetailResponseDto

interface PerfumeService {
    suspend fun getPerfumeTopDetail(perfumeId: String): PerfumeDetailResponseDto
}