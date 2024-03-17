package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.response.PerfumeAgeResponseDto
import javax.inject.Inject

class UpdatePerfumeAgeUseCase @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) {
    suspend operator fun invoke(age: Float, perfumeId: Int): PerfumeAgeResponseDto {
        return perfumeRepository.postPerfumeAge(AgeRequestDto(age.toInt()), perfumeId.toString())
    }
}