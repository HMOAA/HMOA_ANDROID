package com.hmoa.core_datastore.Admin

import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto
import corenetwork.Admin.AdminService
import javax.inject.Inject

class AdminDataStoreImpl @Inject constructor(
    private val adminService: AdminService
) : AdminDataStore {
    override suspend fun deleteHomeMenu(homeMenuId: Int): DataResponseDto<Any> {
        return adminService.deleteHomeMenu(homeMenuId)
    }

    override suspend fun postHomeMenu(
        homeMenuId: Int,
        homeMenuSaveRequestDto: HomeMenuSaveRequestDto
    ): DataResponseDto<Any> {
        return adminService.postHomeMenu(homeMenuId, homeMenuSaveRequestDto)
    }

    override suspend fun postHomePerfume(dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any> {
        return adminService.postHomePerfume(dto)
    }

    override suspend fun postHomePerfumeAdd(homeId: Int, perfumeId: Int): DataResponseDto<Any> {
        return adminService.postHomePerfumeAdd(homeId, perfumeId)
    }
}