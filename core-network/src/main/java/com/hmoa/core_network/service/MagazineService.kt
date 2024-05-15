package com.hmoa.core_network.service

import com.hmoa.core_model.response.MagazineResponseDto
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hmoa.core_model.response.PagingData
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MagazineService {

    @GET("/magazine/{magazineId}")
    suspend fun getMagazine(
        @Path("magazineId") magazineId : Int
    ) : ApiResponse<MagazineResponseDto>

    @PUT("/magazine/{magazineId}/like")
    suspend fun putMagazineHeart(
        @Path("magazineId") magazineId : Int
    ) : ApiResponse<Any>

    @DELETE("/magazine/{magazineId}/like")
    suspend fun deleteMagazineHeart(
        @Path("magazineId") magazineId : Int
    ) : ApiResponse<Any>

    @GET("/magazine/list/cursor")
    suspend fun getMagazineList(
        @Query("cursor") cursor : Int
    ) : ApiResponse<PagingData<MagazineSummaryResponseDto>>

    @GET("/magazine/tastingComment")
    suspend fun getMagazineTastingComment() : ApiResponse<MagazineTastingCommentResponseDto>
}