package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.response.DataResponseDto
import javax.inject.Inject

class GetPerfumeUsecase @Inject constructor(private val perfumeRepository: PerfumeRepository) {
    suspend fun invoke(): DataResponseDto<Any> {
        return perfumeRepository.getLikePerfumes()
    }
}