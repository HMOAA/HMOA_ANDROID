package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.CommunityCommentRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCommunityComment @Inject constructor(
    private val repository : CommunityCommentRepository
) {

    operator fun invoke(id : Int, page : Int) = flow {
        val result = repository.getCommunityComments(
            communityId = id,
            page = page
        )
        emit(result)
    }

}