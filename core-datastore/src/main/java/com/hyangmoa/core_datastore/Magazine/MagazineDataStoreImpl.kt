package com.hyangmoa.core_datastore.Magazine

import ResultResponse
import com.hyangmoa.core_model.data.ErrorMessage
import com.hyangmoa.core_model.response.MagazineResponseDto
import com.hyangmoa.core_model.response.MagazineSummaryResponseDto
import com.hyangmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hyangmoa.core_model.response.PagingData
import com.hyangmoa.core_network.service.MagazineService
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

    override suspend fun getMagazineList(cursor: Int): ResultResponse<PagingData<MagazineSummaryResponseDto>> {
        val result = ResultResponse<PagingData<MagazineSummaryResponseDto>>()
        magazineService.getMagazineList(cursor).suspendOnSuccess{
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