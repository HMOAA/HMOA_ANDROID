package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyCommentUseCase @Inject constructor(
    private val memberRepository : MemberRepository
){

    suspend fun invoke(page : Int) = flow{
        val result = memberRepository.getCommunityComments(page)
            .map{ data ->
                /** 시간에 대한 데이터를 n일 전으로 변경해야 함 */
                data.copy(time = "")
            }
        emit(result)
    }
}