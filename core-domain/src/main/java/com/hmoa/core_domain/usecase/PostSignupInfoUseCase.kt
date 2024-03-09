package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.request.JoinUpdateRequestDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostSignupInfoUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)

    operator fun invoke(age: Int, sex: Boolean, nickname: String) {
        scope.launch { memberRepository.updateJoin(JoinUpdateRequestDto(age, nickname, sex)) }
    }
}