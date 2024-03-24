package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.response.CommunityCommentDefaultResponseDto
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetMyCommentByPostUseCase @Inject constructor(
    private val memberRepository : MemberRepository
){

    operator fun invoke(page : Int) = flow{
        val result = memberRepository.getCommunityComments(page)
            .map{ comment ->
                comment.calDate()
            }
        emit(result)
    }
}