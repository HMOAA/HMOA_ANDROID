package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.data.Perfume
import com.hmoa.core_model.response.PerfumeCommentGetResponseDto
import com.hmoa.core_model.response.PerfumeCommentResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPerfumeUsecase @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) {
    suspend operator fun invoke(perfumeId: String): Flow<Perfume> {
        val perfumeInfo1 = perfumeRepository.getPerfumeTopDetail(perfumeId)
        val perfumeInfo2 = perfumeRepository.getPerfumeBottomDetail(perfumeId)
        val result = Perfume(
            brandEnglishName = perfumeInfo1.brandEnglishName,
            brandKoreanName = perfumeInfo1.brandName,
            brandId = perfumeInfo1.brandId.toString(),
            brandImgUrl = perfumeInfo1.brandImgUrl,
            perfumeEnglishName = perfumeInfo1.englishName,
            perfumeKoreanName = perfumeInfo1.koreanName,
            baseNote = perfumeInfo1.baseNote ?: "",
            heartNote = perfumeInfo1.heartNote ?: "",
            topNote = perfumeInfo1.topNote ?: "",
            likedCount = perfumeInfo1.heartNum,
            liked = perfumeInfo1.liked,
            notePhotos = perfumeInfo1.notePhotos?.map { mapIndexToTastingNoteImageUrl(it.toInt()) } ?: emptyList(),
            perfumeId = perfumeInfo1.perfumeId.toString(),
            perfumeImageUrl = perfumeInfo1.perfumeImageUrl,
            price = "%,d".format(perfumeInfo1.price),
            review = perfumeInfo1.review,
            singleNote = perfumeInfo1.singleNote,
            sortType = perfumeInfo1.sortType,
            perfumeVolumeList = perfumeInfo1.volume ?: emptyArray(),
            perfumeVolume = perfumeInfo1.priceVolume,
            commentInfo = PerfumeCommentGetResponseDto(
                commentCount = perfumeInfo2.commentInfo?.commentCount ?: 0,
                comments = get3CommentAHeadOfCommentCounts(perfumeInfo2.commentInfo?.comments ?: emptyArray())
            ),
            similarPerfumes = perfumeInfo2.similarPerfumes ?: emptyArray()
        )
        return flow {
            emit(result)
        }
    }


    fun get3CommentAHeadOfCommentCounts(commentCounts: Array<PerfumeCommentResponseDto>): Array<PerfumeCommentResponseDto> {
        if (commentCounts.size < 3) return commentCounts
        else return arrayOf(commentCounts[0], commentCounts[1], commentCounts[2])

    }

    fun mapIndexToTastingNoteImageUrl(index: Int): String {
        val imageUrl = when (index) {
            TastingNoteImageUrl.Image1.index -> return TastingNoteImageUrl.Image1.imageUrl
            TastingNoteImageUrl.Image2.index -> return TastingNoteImageUrl.Image2.imageUrl
            TastingNoteImageUrl.Image3.index -> return TastingNoteImageUrl.Image3.imageUrl
            TastingNoteImageUrl.Image4.index -> return TastingNoteImageUrl.Image4.imageUrl
            TastingNoteImageUrl.Image5.index -> return TastingNoteImageUrl.Image5.imageUrl
            TastingNoteImageUrl.Image6.index -> return TastingNoteImageUrl.Image6.imageUrl
            TastingNoteImageUrl.Image7.index -> return TastingNoteImageUrl.Image7.imageUrl
            TastingNoteImageUrl.Image8.index -> return TastingNoteImageUrl.Image8.imageUrl
            TastingNoteImageUrl.Image9.index -> return TastingNoteImageUrl.Image9.imageUrl
            TastingNoteImageUrl.Image10.index -> return TastingNoteImageUrl.Image10.imageUrl
            TastingNoteImageUrl.Image11.index -> return TastingNoteImageUrl.Image11.imageUrl
            TastingNoteImageUrl.Image14.index -> return TastingNoteImageUrl.Image14.imageUrl
            TastingNoteImageUrl.Image15.index -> return TastingNoteImageUrl.Image15.imageUrl
            TastingNoteImageUrl.Image16.index -> return TastingNoteImageUrl.Image16.imageUrl
            TastingNoteImageUrl.Image17.index -> return TastingNoteImageUrl.Image17.imageUrl
            else -> return TastingNoteImageUrl.ImageEmpty.imageUrl
        }

        return imageUrl
    }
}

enum class TastingNoteImageUrl(val index: Int, val imageUrl: String) {
    ImageEmpty(
        index = 0,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/8833f3f3-8bb3-4197-929a-8627ecf31b39"
    ),
    Image1(
        index = 1,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/b803b2ac-fb9e-4462-91f4-b8ce524c2d58"
    ),
    Image2(
        index = 2,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/025518d7-1a30-4ed8-980c-eb781ab9f51e"
    ),
    Image3(
        index = 3,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/281de301-9bf5-4e55-94db-0b166964ca33"
    ),
    Image4(
        index = 4,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/0e12f0a8-af3b-47d3-bae7-3c124544f455"
    ),
    Image5(
        index = 5,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/be805832-5372-4164-b25e-7536358ed515"
    ),
    Image6(
        index = 6,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/f80585ab-9315-424c-934b-1fd343bed935"
    ),
    Image7(
        index = 7,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/8f2bf6f4-da12-4f89-aa81-58334916b6fc"
    ),
    Image8(
        index = 8,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/efcdd725-5962-4a27-bec7-36a444c946d7"
    ),
    Image9(
        index = 9,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/81bf9e45-7f21-4198-9cbf-fb36987255a8"
    ),
    Image10(
        index = 10,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/c5478a26-b47c-4a5a-aea4-70c381831d16"
    ),
    Image11(
        index = 11,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/9e6b7df0-6ea1-413b-a0f2-99218f919252"
    ),
    Image14(
        index = 14,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/6c90e5a2-9911-4d63-8201-14df75568292"
    ),
    Image15(
        index = 15,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/5d9a8440-67e8-4b8c-b306-b3be31fe53a5"
    ),
    Image16(
        index = 16,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/d95aead4-9116-48b2-99df-42021cb4f10c"
    ),
    Image17(
        index = 17,
        imageUrl = "https://github.com/HMOAA/HMOA_ANDROID/assets/67788699/6c5d2111-737f-48bb-b480-5ad883b120c9"
    ),
}