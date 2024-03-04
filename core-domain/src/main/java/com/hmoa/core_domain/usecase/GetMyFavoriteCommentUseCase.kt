package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyFavoriteCommentUseCase @Inject constructor(
    private val memberRepository : MemberRepository
){
    suspend fun invoke(page : Int) : Flow<List<CommunityCommentDefaultResponseDto>> = flow{
        val result = memberRepository.getHearts(page)
            .map{ data ->
                /** 시간을 n일 전으로 변경해 mapping */
                data.copy(time = "")
            }
        emit(result)
    }
}