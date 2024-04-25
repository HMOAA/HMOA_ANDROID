package com.hmoa.core_domain.usecase

import ResultResponse
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.data.UserInfo
import java.time.LocalDateTime
import javax.inject.Inject

class GetMyUserInfoUseCase @Inject constructor(
    private val memberRepository : MemberRepository
){
    suspend operator fun invoke() : ResultResponse<UserInfo> {

        val response = memberRepository.getMember()
        val result = ResultResponse<UserInfo>()
        if (response.exception is Exception){
            result.exception = response.exception
        } else {
            val todayYear = LocalDateTime.now().year
            val birth = todayYear - response.data!!.age + 1
            val gender = if (response.data!!.sex) "남성" else "여성"

            val user = UserInfo(
                birth = birth,
                gender = gender,
                profile = response.data!!.memberImageUrl,
                nickname = response.data!!.nickname,
                provider = response.data!!.provider
            )
            result.data = user
        }
        return result
    }
}