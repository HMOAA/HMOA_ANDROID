package com.hmoa.core_model.response

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Serializable
data class CommunityCommentDefaultResponseDto(
    val content : String,
    val createAt : String,
    val heartCount : Int,
    val id : Int,
    val liked : Boolean,
    val nickname : String,
    val perfumeId : Int,
    val profileImg : String,
    val writed : Boolean
) {
    fun calDate() : CommunityCommentDefaultResponseDto {

        val today = LocalDateTime.now()
        val date = LocalDateTime.parse(createAt)
        val diff = ChronoUnit.DAYS.between(today, date).toInt()
        val diffDay = when(diff){
            0 -> "오늘"
            1 -> "어제"
            else -> "${diff}일 전"
        }

        return this.copy(
            createAt = diffDay
        )
    }
}
