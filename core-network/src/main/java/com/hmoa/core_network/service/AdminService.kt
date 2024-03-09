package com.hmoa.core_network.service

import com.hmoa.core_model.request.HomeMenuSaveRequestDto
import com.hmoa.core_model.response.DataResponseDto
import com.hmoa.core_model.response.HomeMenuPerfumeResponseDto
import retrofit2.http.*

interface AdminService {
    @DELETE("/admin/{homeMenuId}/delete")
    suspend fun deleteHomeMenu(@Path(value = "homeMenuId") homeMenuId: Int): DataResponseDto<Any>

    @GET("/admin/{homeMenuId}/modify")
    suspend fun postHomeMenu(
        @Path(value = "homeMenuId") homeMenuId: Int,
        @Body homeMenuSaveRequestDto: HomeMenuSaveRequestDto
    ): DataResponseDto<Any>

    @GET("/admin/homePerfume")
    suspend fun postHomePerfume(@Body dto: HomeMenuPerfumeResponseDto): DataResponseDto<Any>

    @GET("/admin/homePerfume/add")
    suspend fun postHomePerfumeAdd(
        @Field("homeId") homeId: Int,
        @Field("perfumeId") perfumeId: Int
    ): DataResponseDto<Any>
}