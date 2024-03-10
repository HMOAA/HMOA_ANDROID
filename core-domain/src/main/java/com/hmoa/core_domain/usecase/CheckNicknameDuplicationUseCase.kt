package com.hmoa.core_domain.usecase

import android.content.ContentValues
import android.util.Log
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_model.request.NickNameRequestDto
import javax.inject.Inject

class CheckNicknameDuplicationUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend fun invoke(nickname: String): Boolean {
        Log.i(ContentValues.TAG, "useCase:postExistsNickname")
        return memberRepository.postExistsNickname(NickNameRequestDto(nickname))
    }
}