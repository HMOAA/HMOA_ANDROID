package com.hyangmoa.core_domain.usecase

import ResultResponse
import com.hyangmoa.core_domain.repository.MemberRepository
import com.hyangmoa.core_model.request.JoinUpdateRequestDto
import com.hyangmoa.core_model.response.MemberResponseDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostSignupUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend operator fun invoke(age: Int, sex: Boolean, nickname: String): Flow<ResultResponse<MemberResponseDto>> {
        while (true) {
            val result = memberRepository.updateJoin(JoinUpdateRequestDto(age, nickname, sex))
            return flow {
                emit(result)
                delay(1_000)
            }
        }
    }
}