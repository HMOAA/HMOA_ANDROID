package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.request.NickNameRequestDto
import javax.inject.Inject

class CheckNicknameDuplicationUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun invoke(nickname: String?): Boolean {
        return memberRepository.postExistsNickname(NickNameRequestDto(nickname))
    }
}