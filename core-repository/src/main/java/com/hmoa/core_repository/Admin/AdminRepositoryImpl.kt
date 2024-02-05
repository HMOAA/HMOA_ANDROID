package com.hmoa.core_repository.Admin

import com.hmoa.core_datastore.Admin.AdminDataStore
import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto

class AdminRepositoryImpl(
    private val adminDataStore : AdminDataStore
) : AdminRepository {
    override fun deleteHomeMenu(homeMenuId: Int): DataResponseDto<Any> {
        return adminDataStore.deleteHomeMenu(homeMenuId)
    }

    override fun postHomeMenu(
        homeMenuId: Int,
        homeMenuSaveRequestDto: HomeMenuSaveRequestDto
    ): DataResponseDto<Any> {
        return adminDataStore.postHomeMenu(homeMenuId, homeMenuSaveRequestDto)
    }

    override fun postHomePerfume(dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any> {
        return adminDataStore.postHomePerfume(dto)
    }

    override fun postHomePerfumeAdd(homeId: Int, perfumeId: Int): DataResponseDto<Any> {
        return adminDataStore.postHomePerfumeAdd(homeId, perfumeId)
    }
}