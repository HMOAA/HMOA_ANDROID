package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.request.NickNameRequestDto
import javax.inject.Inject

class CheckNicknameDuplicateUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend operator fun invoke(nickname: String): Boolean {
        return memberRepository.postExistsNickname(NickNameRequestDto(nickname)).data
    }
}