package com.hmoa.core_domain.usecase

import com.hmoa.core_domain.repository.PerfumeRepository
import com.hmoa.core_model.data.Perfume
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
            baseNote = perfumeInfo1.baseNote,
            heartNote = perfumeInfo1.heartNote,
            topNote = perfumeInfo1.topNote,
            likedCount = perfumeInfo1.heartNum,
            liked = perfumeInfo1.liked,
            notePhotos = perfumeInfo1.notePhotos,
            perfumeId = perfumeInfo1.perfumeId.toString(),
            perfumeImageUrl = perfumeInfo1.perfumeImageUrl,
            price = perfumeInfo1.price,
            review = perfumeInfo1.review,
            singleNote = perfumeInfo1.singleNote,
            sortType = perfumeInfo1.sortType,
            perfumeVolumeList = perfumeInfo1.volume,
            perfumeVolume = perfumeInfo1.priceVolume,
            commentInfo = perfumeInfo2.commentInfo,
            similarPerfumes = perfumeInfo2.similarPerfumes ?: emptyArray()
        )
        return flow {
            emit(result)
        }
    }
}