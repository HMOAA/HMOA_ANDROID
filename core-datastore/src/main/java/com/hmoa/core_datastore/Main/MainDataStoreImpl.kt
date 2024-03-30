package com.hmoa.core_datastore.Main

import ResultResponse
import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto
import com.hmoa.core_network.service.MainService
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import javax.inject.Inject

class MainDataStoreImpl @Inject constructor(
    private val mainService: MainService
) : MainDataStore {
    override suspend fun getFirst(): ResultResponse<HomeMenuFirstResponseDto> {
        val result = ResultResponse<HomeMenuFirstResponseDto>()
        mainService.getFirst().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getFirstMenu(): ResultResponse<HomeMenuAllResponseDto> {
        val result = ResultResponse<HomeMenuAllResponseDto>()
        mainService.getFirstMenu().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getSecond(): ResultResponse<List<HomeMenuDefaultResponseDto>> {
        val result = ResultResponse<List<HomeMenuDefaultResponseDto>>()
        mainService.getSecond().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getSecondMenu(): ResultResponse<List<HomeMenuAllResponseDto>> {
        val result = ResultResponse<List<HomeMenuAllResponseDto>>()
        mainService.getSecondMenu().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }

    override suspend fun getThirdMenu(): ResultResponse<List<HomeMenuAllResponseDto>> {
        val result = ResultResponse<List<HomeMenuAllResponseDto>>()
        mainService.getThirdMenu().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            result.exception = Exception(this.statusCode.code.toString())
        }
        return result
    }
}