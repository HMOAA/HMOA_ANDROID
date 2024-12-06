package com.hmoa.feature_hbti

import ResultResponse
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.response.PerfumeRecommendResponseDto
import com.hmoa.core_model.response.PerfumeRecommendsResponseDto
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationResultViewModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class PerfumeRecommendationResultViewmodelTest : TestCase() {
    @get:Rule(order = 0)
    val coroutineRule = TestCoroutineRule()

    private val surveyRepository = mock(SurveyRepository::class.java)
    lateinit var viewmodel: PerfumeRecommendationResultViewModel
    lateinit var notePerfumeRecommendationResult: List<PerfumeRecommendResponseDto>
    lateinit var pricePerfumeRecommendationResult: List<PerfumeRecommendResponseDto>

    @Before
    override fun setUp() {
        super.setUp()
        notePerfumeRecommendationResult = listOf<PerfumeRecommendResponseDto>(
            PerfumeRecommendResponseDto(
                brandname = "샤넬",
                perfumeId = 0,
                perfumeName = "넘버.1",
                perfumeEnglishName = "No.1",
                perfumeImageUrl = "",
                price = 100000
            )
        )
        pricePerfumeRecommendationResult = listOf()
        runBlocking {
            Mockito.`when`(surveyRepository.getNoteSortedPerfumeRecommendsResult())
                .thenReturn(ResultResponse(PerfumeRecommendsResponseDto(recommendPerfumes = notePerfumeRecommendationResult)))
            Mockito.`when`(surveyRepository.getPriceSortedPerfumeRecommendsResult())
                .thenReturn(ResultResponse(PerfumeRecommendsResponseDto(recommendPerfumes = pricePerfumeRecommendationResult)))
            viewmodel = PerfumeRecommendationResultViewModel(surveyRepository)
        }
    }

    @Test
    fun `test_getPriceRecommendationPerfume_EmptyPerfumes`() {
        viewmodel.insertPriceSortedPerfumes()
        assertEquals(viewmodel.perfumesState.value, emptyList<PerfumeRecommendResponseDto>())
    }

    @Test
    fun `test_insertNoteAfterInsertPrice_reflectsInNotePerfumes`() {
        viewmodel.insertPriceSortedPerfumes()
        viewmodel.insertNoteSortedPerfumes()
        assertEquals(viewmodel.perfumesState.value, notePerfumeRecommendationResult)
    }

    @Test
    fun `test_insertNoteAfterInsertPrice_reflectsInIsEmptyPriceContentNeedState`() {
        viewmodel.insertPriceSortedPerfumes()
        viewmodel.insertNoteSortedPerfumes()
        assertEquals(viewmodel.isEmptyPriceContentNeedState.value, false)
    }

    @Test
    fun `test_insertPriceAfterInsertNote_reflectsInIsEmptyPriceContentNeedState`() {
        viewmodel.insertNoteSortedPerfumes()
        viewmodel.insertPriceSortedPerfumes()
        assertEquals(viewmodel.isEmptyPriceContentNeedState.value, true)
    }
}
