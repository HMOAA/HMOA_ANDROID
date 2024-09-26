package com.hmoa.core_domain.usecase

import ResultResponse
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_domain.entity.data.Age
import com.hmoa.core_model.request.AgeRequestDto
import com.hmoa.core_model.response.PerfumeAgeResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePerfumeAgeUseCase @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) {
    operator fun invoke(age: Float, perfumeId: Int): Flow<ResultResponse<PerfumeAgeResponseDto>> {
        return flow {
            val result = perfumeRepository.postPerfumeAge(AgeRequestDto(mapToAgeNumber(age)), perfumeId.toString())
            emit(result)
        }
    }

    fun mapToAgeNumber(ageFloat: Float): Int {
        val ageInt = ageFloat.toInt()
        var result = Age.AGE10.ageNumber
        when {
            10 <= ageInt && ageInt < 20 -> {
                result = Age.AGE10.ageNumber
            }

            20 <= ageInt && ageInt < 30 -> {
                result = Age.AGE20.ageNumber
            }

            30 <= ageInt && ageInt < 40 -> {
                result = Age.AGE30.ageNumber
            }

            40 <= ageInt && ageInt < 50 -> {
                result = Age.AGE40.ageNumber
            }

            50 <= ageInt && ageInt < 60 -> {
                result = Age.AGE50.ageNumber
            }
        }
        return result
    }
}