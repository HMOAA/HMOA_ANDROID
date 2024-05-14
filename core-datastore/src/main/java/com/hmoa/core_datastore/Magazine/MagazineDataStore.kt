package com.hmoa.core_datastore.Magazine

import ResultResponse
import com.hmoa.core_model.response.MagazineListResponseDto
import com.hmoa.core_model.response.MagazineResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto

interface MagazineDataStore {
    suspend fun getMagazine(magazineId : Int) : ResultResponse<MagazineResponseDto>
    suspend fun putMagazineHeart(magazineId : Int) : ResultResponse<Any>
    suspend fun deleteMagazineHeart(magazineId : Int) : ResultResponse<Any>
    suspend fun getMagazineList(page : Int) : ResultResponse<MagazineListResponseDto>
    suspend fun getMagazineTastingComment() : ResultResponse<MagazineTastingCommentResponseDto>
}