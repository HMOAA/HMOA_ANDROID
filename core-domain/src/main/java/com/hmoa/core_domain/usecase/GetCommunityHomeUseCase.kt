package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.CommunityRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCommunityHomeUseCase @Inject constructor(
    private val repository : CommunityRepository
) {

    operator fun invoke() : Flow<List<CommunityByCategoryResponseDto>> = flow {
        emit(repository.getCommunitiesHome())
    }

}