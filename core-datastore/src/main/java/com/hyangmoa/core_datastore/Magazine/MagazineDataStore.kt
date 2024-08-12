package com.hyangmoa.core_datastore.Magazine

import ResultResponse
import com.hyangmoa.core_model.response.MagazineResponseDto
import com.hyangmoa.core_model.response.MagazineSummaryResponseDto
import com.hyangmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hyangmoa.core_model.response.PagingData

interface MagazineDataStore {
    suspend fun getMagazine(magazineId : Int) : ResultResponse<MagazineResponseDto>
    suspend fun putMagazineHeart(magazineId : Int) : ResultResponse<Any>
    suspend fun deleteMagazineHeart(magazineId : Int) : ResultResponse<Any>
    suspend fun getMagazineList(cursor : Int) : ResultResponse<PagingData<MagazineSummaryResponseDto>>
    suspend fun getMagazineTastingComment() : ResultResponse<MagazineTastingCommentResponseDto>
}