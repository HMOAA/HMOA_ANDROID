package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.request.JoinUpdateRequestDto
import com.hmoa.core_model.response.MemberResponseDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostSignupInfoUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(age: Int, sex: Boolean, nickname: String): Flow<MemberResponseDto> {
        return flow {
            emit(memberRepository.updateJoin(JoinUpdateRequestDto(age, nickname, sex)))
        }
    }
}