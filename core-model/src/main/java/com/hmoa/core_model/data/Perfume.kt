package com.hmoa.core_model.data

import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeReviewResponseDto
import com.hmoa.core_model.response.PerfumeSimilarResponseDto

data class Perfume(
    val brandEnglishName: String,
    val brandKoreanName: String,
    val brandId: String,
    val brandImgUrl: String,
    val perfumeEnglishName: String,
    val perfumeKoreanName: String,
    val baseNote: String,
    val heartNote: String,
    val topNote: String,
    var likedCount: Int,
    var liked: Boolean,
    val notePhotos: Array<String>,
    val perfumeId: String,
    val perfumeImageUrl: String,
    val price: Int,
    val review: PerfumeReviewResponseDto,
    val singleNote: Array<String>?,
    val sortType: Int,
    val perfumeVolumeList: Array<Int>,
    val perfumeVolume: Int,
    val commentInfo: PerfumeCommentGetResponseDto,
    val similarPerfumes: Array<PerfumeSimilarResponseDto>
)
