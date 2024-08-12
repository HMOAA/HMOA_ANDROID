package com.hyangmoa.core_domain.usecase

import ResultResponse
import com.hyangmoa.core_domain.repository.PerfumeCommentRepository
import com.hyangmoa.core_model.response.DataResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateLikePerfumeCommentUseCase @Inject constructor(
    private val perfumeCommentRepository: PerfumeCommentRepository
) {
    suspend operator fun invoke(isLike: Boolean, commentId: Int): Flow<ResultResponse<DataResponseDto<Nothing?>>> {
        var result = ResultResponse<DataResponseDto<Nothing?>>()
        when (isLike) {
            true -> {
                result = perfumeCommentRepository.putPerfumeCommentLike(commentId = commentId)
            }

            false -> {
                result = perfumeCommentRepository.deletePerfumeCommentLike(commentId = commentId)
            }
        }
        return flow { emit(result) }
    }
}