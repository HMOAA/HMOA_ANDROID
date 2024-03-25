package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.data.UserInfo
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetMyUserInfoUseCase @Inject constructor(
    private val memberRepository : MemberRepository
){

    operator fun invoke() = flow{
        val result = memberRepository.getMember()

        val todayYear = LocalDateTime.now().year

        val birth = todayYear - result.age + 1
        val gender = if (result.sex) "male" else "female"

        val user = UserInfo(
            birth = birth,
            gender = gender,
            profile = result.memberImageUrl,
            nickname = result.nickname,
            provider = result.provider
        )
        emit(user)
    }

}