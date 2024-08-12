package com.hyangmoa.core_repository

import com.hyangmoa.core_datastore.Admin.AdminDataStore
import com.hyangmoa.core_model.request.HomeMenuSaveRequestDto
import com.hyangmoa.core_model.response.DataResponseDto
import com.hyangmoa.core_model.response.HomeMenuPerfumeResponseDto
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminDataStore: AdminDataStore
) : com.hyangmoa.core_domain.repository.AdminRepository {
    override suspend fun deleteHomeMenu(homeMenuId: Int): DataResponseDto<Any> {
        return adminDataStore.deleteHomeMenu(homeMenuId)
    }

    override suspend fun postHomeMenu(
        homeMenuId: Int,
        homeMenuSaveRequestDto: HomeMenuSaveRequestDto
    ): DataResponseDto<Any> {
        return adminDataStore.postHomeMenu(homeMenuId, homeMenuSaveRequestDto)
    }

    override suspend fun postHomePerfume(dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any> {
        return adminDataStore.postHomePerfume(dto)
    }

    override suspend fun postHomePerfumeAdd(homeId: Int, perfumeId: Int): DataResponseDto<Any> {
        return adminDataStore.postHomePerfumeAdd(homeId, perfumeId)
    }
}