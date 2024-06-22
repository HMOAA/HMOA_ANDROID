package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumerRepository
import com.hmoa.core_model.response.PerfumerDescResponseDto
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