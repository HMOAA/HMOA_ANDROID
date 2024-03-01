package com.hmoa.core_domain.repository

import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto

interface MainRepository {
    suspend fun getFirst(): HomeMenuFirstResponseDto
    suspend fun getFirstMenu(): HomeMenuAllResponseDto
    suspend fun getSecond(): HomeMenuDefaultResponseDto
    suspend fun getSecondMenu(): List<HomeMenuAllResponseDto>
    suspend fun getThirdMenu(): List<HomeMenuAllResponseDto>
}