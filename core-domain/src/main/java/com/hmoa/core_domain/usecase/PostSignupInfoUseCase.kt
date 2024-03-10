package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.response.MemberResponseDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostSignupInfoUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke(age: Int, sex: Boolean, nickname: String): Flow<MemberResponseDto> {

        val result = memberRepository.updateJoin(JoinUpdateRequestDto(age, nickname, sex))
        return flow {
            emit(result)
            delay(1_000)
        }
    }
}