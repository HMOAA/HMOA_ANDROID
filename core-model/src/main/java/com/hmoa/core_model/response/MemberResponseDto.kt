package com.hmoa.core_model.response

import com.hmoa.core_data.Provider

data class MemberResponseDto(
    val age: Int,
    val memberId: Int,
    val memberImageUrl: String,
    val nickname: String,
    var provider: String,
    val sex: Boolean
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
