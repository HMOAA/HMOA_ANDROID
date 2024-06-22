package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.TermRepository
import com.hmoa.core_model.response.TermDescResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTermUseCase @Inject constructor(private val termRepository: TermRepository) {
    suspend operator fun invoke(id: Int): Flow<TermDescResponseDto?> {
        val result = termRepository.getTerm(id)
        return flow {
            if (result.errorMessage != null) {
                throw Exception(result.errorMessage!!.message)
            }
            emit(result.data?.data)
        }
    }
}