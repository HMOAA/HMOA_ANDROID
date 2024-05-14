package com.hmoa.core_repository

import ResultResponse
import com.hmoa.core_datastore.Magazine.MagazineDataStore
import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_model.response.MagazineListResponseDto
import com.hmoa.core_model.response.MagazineResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto
import javax.inject.Inject

class MagazineRepositoryImpl @Inject constructor(
    private val magazineDataStore: MagazineDataStore
) : MagazineRepository {
    override suspend fun getMagazine(magazineId: Int): ResultResponse<MagazineResponseDto> {
        return magazineDataStore.getMagazine(magazineId)
    }

    override suspend fun putMagazineHeart(magazineId: Int): ResultResponse<Any> {
        return magazineDataStore.putMagazineHeart(magazineId)
    }

    override suspend fun deleteMagazineHeart(magazineId: Int): ResultResponse<Any> {
        return magazineDataStore.deleteMagazineHeart(magazineId)
    }

    override suspend fun getMagazineList(page: Int): ResultResponse<MagazineListResponseDto> {
        return magazineDataStore.getMagazineList(page)
    }

    override suspend fun getMagazineTastingComment(): ResultResponse<MagazineTastingCommentResponseDto> {
        return magazineDataStore.getMagazineTastingComment()
    }
}