package com.hyangmoa.core_domain.usecase

import com.hyangmoa.core_domain.repository.PerfumerRepository
import com.hyangmoa.core_model.response.PerfumerDescResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPerfumerUseCase @Inject constructor(private val perfumerRepository: PerfumerRepository) {
    suspend operator fun invoke(id: Int): Flow<PerfumerDescResponseDto?> {
        val result = perfumerRepository.getPerfumer(id)
        return flow {
            if (result.errorMessage != null) {
                throw Exception(result.errorMessage!!.message)
            }
            emit(result.data?.data)
        }
    }
}