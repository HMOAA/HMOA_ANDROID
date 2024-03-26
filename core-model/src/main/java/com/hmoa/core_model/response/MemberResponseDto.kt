package com.hmoa.core_model.response

import com.hmoa.core_model.Provider
import kotlinx.serialization.Serializable

@Serializable
data class MemberResponseDto(
    var age: Int,
    var memberId: Int,
    var memberImageUrl: String,
    var nickname: String,
    var provider: String,
    var sex: Boolean
) {
    init {
        provider = when (provider) {
            Provider.APPLE.name -> Provider.APPLE.name
            Provider.GOOGLE.name -> Provider.GOOGLE.name
            Provider.KAKAO.name -> Provider.KAKAO.name
            else -> throw IllegalArgumentException("Invalide provider: $provider")
        }
    }
}
