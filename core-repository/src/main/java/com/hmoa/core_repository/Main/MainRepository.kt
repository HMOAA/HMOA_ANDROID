package com.hmoa.core_repository.Main

import com.hmoa.core_model.response.HomeMenuAllResponseDto
import com.hmoa.core_model.response.HomeMenuDefaultResponseDto
import com.hmoa.core_model.response.HomeMenuFirstResponseDto

interface MainRepository {
    fun getFirst() : HomeMenuFirstResponseDto
    fun getFirstMenu() : HomeMenuAllResponseDto
    fun getSecond() : HomeMenuDefaultResponseDto
    fun getSecondMenu() : List<HomeMenuAllResponseDto>
    fun getThirdMenu() : List<HomeMenuAllResponseDto>
}