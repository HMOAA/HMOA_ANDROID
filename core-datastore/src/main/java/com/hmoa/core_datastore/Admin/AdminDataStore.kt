package com.hmoa.core_datastore.Admin

import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto

interface AdminDataStore {
    fun deleteHomeMenu(homeMenuId : Int) : DataResponseDto<Any>
    fun postHomeMenu(homeMenuId : Int, homeMenuSaveRequestDto : HomeMenuSaveRequestDto) : DataResponseDto<Any>
    fun postHomePerfume(dto : HomeMenuPerfumeResponseDto) : DataResponseDto<Any>
    fun postHomePerfumeAdd(homeId : Int, perfumeId : Int) : DataResponseDto<Any>
}