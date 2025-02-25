package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MagazineRepository
import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.response.MagazineTastingCommentResponseDto
import com.hmoa.core_model.response.RecentPerfumeResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MagazineMainUseCase @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val magazineRepository: MagazineRepository
){
    suspend fun invoke(): Flow<Pair<RecentPerfumeResponseDto, MagazineTastingCommentResponseDto>> {
        val perfumeFlow = flow{
            val result = perfumeRepository.getRecentPerfumes()
            if (result.errorMessage != null){ throw Exception(result.errorMessage!!.message) }
            emit(result.data!!)
        }

        val communityFlow = flow{
            val result = magazineRepository.getMagazineTastingComment()
            if(result.errorMessage != null){ throw Exception(result.errorMessage!!.message) }
            emit(result.data!!)
        }

        return combine(perfumeFlow, communityFlow){ perfumes, communities -> Pair(perfumes, communities) }
    }
}