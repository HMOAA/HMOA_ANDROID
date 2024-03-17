package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.PerfumeGender
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.response.PerfumeGenderResponseDto
import javax.inject.Inject

class UpdatePerfumeGenderUseCase @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) {
    suspend operator fun invoke(gender: PerfumeGender, perfumeId: Int): PerfumeGenderResponseDto {
        val param = when (gender) {
            PerfumeGender.MALE -> 1
            PerfumeGender.FEMALE -> 2
            PerfumeGender.NEUTRAL -> 3
        }
        return perfumeRepository.postPerfumeGender(PerfumeGenderRequestDto(param), perfumeId.toString())
    }
}