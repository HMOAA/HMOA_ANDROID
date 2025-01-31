package com.hmoa.core_domain.usecase

import ResultResponse
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_domain.entity.data.PerfumeGender
import com.hmoa.core_model.request.PerfumeGenderRequestDto
import com.hmoa.core_model.response.PerfumeGenderResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePerfumeGenderUseCase @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) {
    operator fun invoke(gender: PerfumeGender, perfumeId: Int): Flow<ResultResponse<PerfumeGenderResponseDto>> {
        val param = when (gender) {
            PerfumeGender.MALE -> 1
            PerfumeGender.FEMALE -> 2
            PerfumeGender.NEUTRAL -> 3
        }
        return flow {
            val result = perfumeRepository.postPerfumeGender(PerfumeGenderRequestDto(param), perfumeId.toString())
            emit(result)
        }
    }
}