package com.hyangmoa.core_domain.repository

import ResultResponse
import com.hyangmoa.core_model.response.MagazineResponseDto
import com.hyangmoa.core_model.response.MagazineSummaryResponseDto
import com.hyangmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hyangmoa.core_model.response.PagingData

interface MagazineRepository {
    suspend fun getMagazine(magazineId : Int) : ResultResponse<MagazineResponseDto>
    suspend fun putMagazineHeart(magazineId : Int) : ResultResponse<Any>
    suspend fun deleteMagazineHeart(magazineId : Int) : ResultResponse<Any>
    suspend fun getMagazineList(cursor : Int) : ResultResponse<PagingData<MagazineSummaryResponseDto>>
    suspend fun getMagazineTastingComment() : ResultResponse<MagazineTastingCommentResponseDto>
}