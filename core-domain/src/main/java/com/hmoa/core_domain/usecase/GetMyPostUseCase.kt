package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.CommunityByCategoryResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyPostUseCase @Inject constructor(
    private val memberRepository : MemberRepository,
){

    operator fun invoke(page : Int) : Flow<List<CommunityByCategoryResponseDto>> = flow{
        val result = memberRepository.getCommunities(page)
        emit(result)
    }

}