package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.response.DataResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePerfumeLikeUseCase @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) {
    private lateinit var result: DataResponseDto<Nothing>
    suspend operator fun invoke(like: Boolean, perfumeId: Int): Flow<DataResponseDto<Any>> {
        when (like) {
            true -> {
                return flow { emit(perfumeRepository.putPerfumeLike(perfumeId.toString())) }
            }

            false -> {
                return flow { emit(perfumeRepository.deletePerfumeLike(perfumeId.toString())) }
            }
        }

    }
}