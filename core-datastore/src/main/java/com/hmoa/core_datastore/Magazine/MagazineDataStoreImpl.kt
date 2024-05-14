package com.hmoa.core_datastore.Magazine

import ResultResponse
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.response.MagazineListResponseDto
import com.hmoa.core_model.response.MagazineResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hmoa.core_network.service.MagazineService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MagazineDataStoreImpl @Inject constructor(
    private val magazineService: MagazineService
) : MagazineDataStore {
    override suspend fun getMagazine(magazineId: Int): ResultResponse<MagazineResponseDto> {
        val result = ResultResponse<MagazineResponseDto>()
        magazineService.getMagazine(magazineId).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }
    override suspend fun putMagazineHeart(magazineId: Int): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        magazineService.putMagazineHeart(magazineId).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun deleteMagazineHeart(magazineId: Int): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        magazineService.deleteMagazineHeart(magazineId).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun getMagazineList(page: Int): ResultResponse<MagazineListResponseDto> {
        val result = ResultResponse<MagazineListResponseDto>()
        magazineService.getMagazineList(page).suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }

    override suspend fun getMagazineTastingComment(): ResultResponse<MagazineTastingCommentResponseDto> {
        val result = ResultResponse<MagazineTastingCommentResponseDto>()
        magazineService.getMagazineTastingComment().suspendOnSuccess{
            result.data = this.data
        }.suspendOnError{
            result.errorMessage = Json.decodeFromString<ErrorMessage>(this.message())
        }
        return result
    }
}