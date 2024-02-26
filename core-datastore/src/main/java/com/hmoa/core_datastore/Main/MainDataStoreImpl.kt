package com.hmoa.core_datastore.Main

import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto
import corenetwork.Main.MainService

private class MainDataStoreImpl constructor(
    private val mainService: MainService
) : MainDataStore {
    override suspend fun getFirst(): HomeMenuFirstResponseDto {
        return mainService.getFirst()
    }

    override suspend fun getFirstMenu(): HomeMenuAllResponseDto {
        return mainService.getFirstMenu()
    }

    override suspend fun getSecond(): HomeMenuDefaultResponseDto {
        return mainService.getSecond()
    }

    override suspend fun getSecondMenu(): List<HomeMenuAllResponseDto> {
        return mainService.getSecondMenu()
    }

    override suspend fun getThirdMenu(): List<HomeMenuAllResponseDto> {
        return mainService.getThirdMenu()
    }
}