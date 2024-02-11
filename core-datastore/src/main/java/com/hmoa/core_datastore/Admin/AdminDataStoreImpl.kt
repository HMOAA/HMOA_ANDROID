package com.hmoa.core_datastore.Admin

import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto

private class AdminDataStoreImpl : AdminDataStore {
    override fun deleteHomeMenu(homeMenuId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postHomeMenu(
        homeMenuId: Int,
        homeMenuSaveRequestDto: HomeMenuSaveRequestDto
    ): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postHomePerfume(dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }

    override fun postHomePerfumeAdd(homeId: Int, perfumeId: Int): DataResponseDto<Any> {
        TODO("Not yet implemented")
    }
}