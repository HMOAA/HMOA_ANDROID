package com.hmoa.core_network.service

import com.hmoa.core_model.response.MagazineListResponseDto
import com.hmoa.core_model.response.MagazineResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto
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

    @GET("/magazine/list")
    suspend fun getMagazineList(
        @Query("page") page : Int
    ) : ApiResponse<MagazineListResponseDto>

    @GET("/magazine/tastingComment")
    suspend fun getMagazineTastingComment() : ApiResponse<MagazineTastingCommentResponseDto>
}