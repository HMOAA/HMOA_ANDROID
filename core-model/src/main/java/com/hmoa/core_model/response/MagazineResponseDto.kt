package com.hmoa.core_model.response

data class MagazineResponseDto(
    val contents: List<MagazineContent>,
    val createAt: String,
    val likeCount: Int,
    val liked: Boolean,
    val magazineId: Int,
    val preview: String,
    val previewImgUrl: String,
    val tags: List<String>,
    val title: String,
    val viewCount: Int
)