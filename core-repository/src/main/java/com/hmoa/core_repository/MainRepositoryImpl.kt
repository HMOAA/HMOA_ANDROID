package com.hmoa.core_repository

import com.hmoa.core_datastore.Main.MainDataStore
import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainDataStore: MainDataStore
) : com.hmoa.core_domain.repository.MainRepository {

    override suspend fun getFirst(): HomeMenuFirstResponseDto {
        return mainDataStore.getFirst()
    }

    override suspend fun getFirstMenu(): HomeMenuAllResponseDto {
        return mainDataStore.getFirstMenu()
    }

    override suspend fun getSecond(): HomeMenuDefaultResponseDto {
        return mainDataStore.getSecond()
    }

    override suspend fun getSecondMenu(): List<HomeMenuAllResponseDto> {
        return mainDataStore.getSecondMenu()
    }

    override suspend fun getThirdMenu(): List<HomeMenuAllResponseDto> {
        return mainDataStore.getThirdMenu()
    }
}