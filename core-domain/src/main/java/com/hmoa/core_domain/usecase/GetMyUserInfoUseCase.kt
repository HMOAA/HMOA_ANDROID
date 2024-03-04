package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyUserInfoUseCase @Inject constructor(
    private val memberRepository : MemberRepository
){

    suspend fun invoke() = flow{
        emit(memberRepository.getMember())
    }

}