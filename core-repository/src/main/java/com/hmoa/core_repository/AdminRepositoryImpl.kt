package com.hmoa.core_repository

import com.hmoa.core_datastore.Admin.AdminDataStore
import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminDataStore: AdminDataStore
) : com.hmoa.core_domain.repository.AdminRepository {
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