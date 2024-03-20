package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumeCommentRepository
import javax.inject.Inject

class GetPagingPerfumeCommentUserCase @Inject constructor(
    private val perfumeCommentRepository: PerfumeCommentRepository
) {
    suspend operator fun invoke() {
    }
}