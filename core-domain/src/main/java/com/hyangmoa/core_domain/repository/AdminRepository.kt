package com.hyangmoa.core_domain.repository

import com.hyangmoa.core_model.request.HomeMenuSaveRequestDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.HomeMenuPerfumeResponseDto

interface AdminRepository {
    suspend fun deleteHomeMenu(homeMenuId: Int): DataResponseDto<Any>
    suspend fun postHomeMenu(homeMenuId: Int, homeMenuSaveRequestDto: HomeMenuSaveRequestDto): DataResponseDto<Any>
    suspend fun postHomePerfume(dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any>
    suspend fun postHomePerfumeAdd(homeId: Int, perfumeId: Int): DataResponseDto<Any>
}