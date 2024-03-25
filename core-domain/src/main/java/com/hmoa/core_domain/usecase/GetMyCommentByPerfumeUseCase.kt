package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetMyCommentByPerfumeUseCase @Inject constructor(
    private val memberRepository: MemberRepository
){

    operator fun invoke(page : Int) : Flow<List<CommunityCommentDefaultResponseDto>> = flow {
        val result = memberRepository.getPerfumeComments(page)
            .map{comment ->
                comment.calDate()
            }
        emit(result)
    }

}