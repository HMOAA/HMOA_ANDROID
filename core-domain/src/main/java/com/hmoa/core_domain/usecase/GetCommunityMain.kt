package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.CommunityRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCommunityMain @Inject constructor(
    private val repository : CommunityRepository
) {

    operator fun invoke(
        category : String,
        page : Int,
    ) = flow {
        val result = repository.getCommunityByCategory(
            category = category,
            page = page,
        )
        emit(result)
    }

}