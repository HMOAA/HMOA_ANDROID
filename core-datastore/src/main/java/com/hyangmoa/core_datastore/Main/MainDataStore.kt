package com.hyangmoa.core_datastore.Main

import ResultResponse
import com.hyangmoa.core_model.response.HomeMenuAllResponseDto
import com.hyangmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hyangmoa.core_model.response.HomeMenuFirstResponseDto

interface MainDataStore {
    suspend fun getFirst(): ResultResponse<HomeMenuFirstResponseDto>
    suspend fun getFirstMenu(): ResultResponse<List<HomeMenuAllResponseDto>>
    suspend fun getSecond(): ResultResponse<List<HomeMenuDefaultResponseDto>>
    suspend fun getSecondMenu(): ResultResponse<List<HomeMenuAllResponseDto>>
    suspend fun getThirdMenu(): ResultResponse<List<HomeMenuAllResponseDto>>
}