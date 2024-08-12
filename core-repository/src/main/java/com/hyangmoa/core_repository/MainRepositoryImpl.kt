package com.hyangmoa.core_repository

import ResultResponse
import com.hyangmoa.core_datastore.Main.MainDataStore
import com.hyangmoa.core_model.response.HomeMenuAllResponseDto
import com.hyangmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hyangmoa.core_model.response.HomeMenuFirstResponseDto
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainDataStore: MainDataStore
) : com.hyangmoa.core_domain.repository.MainRepository {

    override suspend fun getFirst(): ResultResponse<HomeMenuFirstResponseDto> {
        return mainDataStore.getFirst()
    }

    override suspend fun getFirstMenu(): ResultResponse<List<HomeMenuAllResponseDto>> {
        return mainDataStore.getFirstMenu()
    }

    override suspend fun getSecond(): ResultResponse<List<HomeMenuDefaultResponseDto>> {
        return mainDataStore.getSecond()
    }

    override suspend fun getSecondMenu(): ResultResponse<List<HomeMenuAllResponseDto>> {
        return mainDataStore.getSecondMenu()
    }

    override suspend fun getThirdMenu(): ResultResponse<List<HomeMenuAllResponseDto>> {
        return mainDataStore.getThirdMenu()
    }
}