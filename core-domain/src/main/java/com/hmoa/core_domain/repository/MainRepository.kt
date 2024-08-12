package com.hmoa.core_domain.repository

import ResultResponse
import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto

interface MainRepository {
    suspend fun getFirst(): ResultResponse<HomeMenuFirstResponseDto>
    suspend fun getFirstMenu(): ResultResponse<List<HomeMenuAllResponseDto>>
    suspend fun getSecond(): ResultResponse<List<HomeMenuDefaultResponseDto>>
    suspend fun getSecondMenu(): ResultResponse<List<HomeMenuAllResponseDto>>
    suspend fun getThirdMenu(): ResultResponse<List<HomeMenuAllResponseDto>>
}