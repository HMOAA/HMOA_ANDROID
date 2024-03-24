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

class GetMyFavoriteCommentByPostUseCase @Inject constructor(
    private val memberRepository : MemberRepository
){
    operator fun invoke(page : Int) : Flow<List<CommunityCommentDefaultResponseDto>> = flow{
        val result = memberRepository.getCommunityFavoriteComments(page)
            .map{ comment ->
                /// 시간 계산 후 몇일 전인지 변환
                val dateFormatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                val time = dateFormatter.parse(comment.createAt) ?: Date(0L)

                val today = Calendar.getInstance().timeInMillis

                val dateDiff = (today - time.time) / (24 * 60 * 60 * 1000)

                CommunityCommentDefaultResponseDto(
                    content = comment.content,
                    createAt = "${dateDiff}일 전",
                    heartCount = comment.heartCount,
                    id = comment.id,
                    liked = comment.liked,
                    nickname = comment.nickname,
                    perfumeId = comment.perfumeId,
                    profileImg = comment.profileImg,
                    writed = comment.writed
                )
            }
        emit(result)
    }
}