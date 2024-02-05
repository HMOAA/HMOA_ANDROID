package com.hmoa.core_repository.Main

import com.hmoa.core_datastore.Main.MainDataStore
import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto

class MainRepositoryImpl(
    private val mainDataStore: MainDataStore
) : MainRepository {

    override fun getFirst(): HomeMenuFirstResponseDto {
        return mainDataStore.getFirst()
    }

    override fun getFirstMenu(): HomeMenuAllResponseDto {
        return mainDataStore.getFirstMenu()
    }

    override fun getSecond(): HomeMenuDefaultResponseDto {
        return mainDataStore.getSecond()
    }

    override fun getSecondMenu(): List<HomeMenuAllResponseDto> {
        return mainDataStore.getSecondMenu()
    }

    override fun getThirdMenu(): List<HomeMenuAllResponseDto> {
        return mainDataStore.getThirdMenu()
    }
}