package com.hmoa.core_datastore.Main

import ResultResponse
import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto
import com.hmoa.core_network.authentication.Authenticator
import com.hmoa.core_network.service.MainService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class MainDataStoreImpl @Inject constructor(
    private val mainService: MainService,
    private val authenticator: Authenticator
) : MainDataStore {
    override suspend fun getFirst(): ResultResponse<HomeMenuFirstResponseDto> {
        val result = ResultResponse<HomeMenuFirstResponseDto>()
        mainService.getFirst().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    mainService.getFirst().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getFirstMenu(): ResultResponse<List<HomeMenuAllResponseDto>> {
        val result = ResultResponse<List<HomeMenuAllResponseDto>>()
        mainService.getFirstMenu().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    mainService.getFirstMenu().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getSecond(): ResultResponse<List<HomeMenuDefaultResponseDto>> {
        val result = ResultResponse<List<HomeMenuDefaultResponseDto>>()
        mainService.getSecond().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    mainService.getSecond().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getSecondMenu(): ResultResponse<List<HomeMenuAllResponseDto>> {
        val result = ResultResponse<List<HomeMenuAllResponseDto>>()
        mainService.getSecondMenu().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    mainService.getSecondMenu().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }

    override suspend fun getThirdMenu(): ResultResponse<List<HomeMenuAllResponseDto>> {
        val result = ResultResponse<List<HomeMenuAllResponseDto>>()
        mainService.getThirdMenu().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                handleErrorMesssage = { result.errorMessage = it },
                onCompleteTokenRefresh = {
                    mainService.getThirdMenu().suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}