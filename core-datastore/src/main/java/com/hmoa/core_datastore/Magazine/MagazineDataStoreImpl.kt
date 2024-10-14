package com.hmoa.core_datastore.Magazine

import ResultResponse
import com.hmoa.core_model.response.MagazineResponseDto
import com.hmoa.core_model.response.MagazineSummaryResponseDto
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hmoa.core_model.response.PagingData
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.MagazineService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class MagazineDataStoreImpl @Inject constructor(
    private val magazineService: MagazineService,
    private val authenticator: Authenticator
) : MagazineDataStore {
    override suspend fun getMagazine(magazineId: Int): ResultResponse<MagazineResponseDto> {
        val result = ResultResponse<MagazineResponseDto>()
        magazineService.getMagazine(magazineId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    magazineService.getMagazine(magazineId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun putMagazineHeart(magazineId: Int): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        magazineService.putMagazineHeart(magazineId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    magazineService.putMagazineHeart(magazineId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun deleteMagazineHeart(magazineId: Int): ResultResponse<Any> {
        val result = ResultResponse<Any>()
        magazineService.deleteMagazineHeart(magazineId).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    magazineService.deleteMagazineHeart(magazineId).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getMagazineList(cursor: Int): ResultResponse<PagingData<MagazineSummaryResponseDto>> {
        val result = ResultResponse<PagingData<MagazineSummaryResponseDto>>()
        magazineService.getMagazineList(cursor).suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    magazineService.getMagazineList(cursor).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getMagazineTastingComment(): ResultResponse<MagazineTastingCommentResponseDto> {
        val result = ResultResponse<MagazineTastingCommentResponseDto>()
        magazineService.getMagazineTastingComment().suspendOnSuccess {
            result.data = this.data
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    magazineService.getMagazineTastingComment().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}