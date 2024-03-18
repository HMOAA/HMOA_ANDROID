package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.response.PerfumeAgeResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePerfumeAgeUseCase @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) {
    operator fun invoke(age: Float, perfumeId: Int): Flow<PerfumeAgeResponseDto> {
        return flow {
            val result = perfumeRepository.postPerfumeAge(AgeRequestDto(age.toInt()), perfumeId.toString())
            emit(result)
        }
    }
}