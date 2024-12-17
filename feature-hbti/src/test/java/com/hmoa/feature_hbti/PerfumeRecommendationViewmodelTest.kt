package com.hmoa.feature_hbti

import ResultResponse
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_domain.usecase.CalculateMinAndMaxPriceOutOfStringUseCase
import com.hmoa.core_domain.usecase.GetPerfumeSurveyUseCase
import com.hmoa.core_model.PerfumeRecommendType
import com.hmoa.core_model.data.NoteCategoryTag
import com.hmoa.core_model.data.PerfumeSurveyContents
import com.hmoa.core_model.request.PerfumeSurveyAnswerRequestDto
import com.hmoa.core_model.response.PerfumeRecommendResponseDto
import com.hmoa.core_model.response.PerfumeRecommendsResponseDto
import com.hmoa.feature_hbti.viewmodel.PerfumeRecommendationViewModel
import junit.framework.TestCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class PerfumeRecommendationViewmodelTest : TestCase() {
    @get:Rule(order = 0)
    val coroutineRule = TestCoroutineRule()

    private val getPerfumeSurveyUseCase = mock(GetPerfumeSurveyUseCase::class.java)
    private val calculateMinAndMaxPriceOutOfStringUseCase = mock(CalculateMinAndMaxPriceOutOfStringUseCase::class.java)
    private val surveyRepository = mock(SurveyRepository::class.java)
    lateinit var viewmodel: PerfumeRecommendationViewModel

    companion object {
        val noteCategoryTags = listOf(
            NoteCategoryTag(
                category = "시트러스",
                note = listOf("라임 만다린", "베르가뭇", "비터오렌지", "자몽"),
                isSelected = listOf(false, false, false, false)
            ),
            NoteCategoryTag(
                category = "플로럴",
                note = listOf("자스민", "라벤더", "핑크로즈", "화이트로즈", "바이올렛", "피오니"),
                isSelected = listOf(false, false, false, false, false, false)
            ),
            NoteCategoryTag(
                category = "우디",
                note = listOf("샌달우드", "시더우드", "베티버", "페츌리"),
                isSelected = listOf(false, false, false, false)
            )
        )
        val perfumeSurveyQuestions = ResultResponse<PerfumeSurveyContents>(
            data = PerfumeSurveyContents(
                priceQuestionTitle = "선호하시는 가격대를 알려주세요",
                priceQuestionOptions = listOf("50,000원 이하", "50,000 ~ 100,000원", "100,000원 이상"),
                priceQuestionOptionIds = listOf(0, 1, 2),
                noteQuestionTitle = "시향 후 마음에 드는 향료를 골라주세요",
                noteCategoryTags = noteCategoryTags,
                isPriceMultipleChoice = true
            ),
            errorMessage = null
        )
        val fakeSurveyAnswerDto = PerfumeSurveyAnswerRequestDto(
            minPrice = 50000,
            maxPrice = 100000,
            notes = listOf("핑크로즈")
        )
        val fakeSurveyAnswerResponse = ResultResponse<PerfumeRecommendsResponseDto>(
            data = PerfumeRecommendsResponseDto(
                recommendPerfumes = listOf(
                    PerfumeRecommendResponseDto(
                        brandname = "샤넬",
                        perfumeId = 0,
                        perfumeEnglishName = "channel 05",
                        perfumeName = "샤넬 05",
                        perfumeImageUrl = "image",
                        price = 90000
                    )
                )
            ),
            errorMessage = null
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        runBlocking {
            Mockito.`when`(getPerfumeSurveyUseCase.invoke()).thenReturn(perfumeSurveyQuestions)
            Mockito.`when`(
                surveyRepository.postPerfumeSurveyAnswers(
                    dto = fakeSurveyAnswerDto, recommendType = PerfumeRecommendType.PRICE
                )
            ).thenReturn(fakeSurveyAnswerResponse)
            viewmodel = PerfumeRecommendationViewModel(
                getPerfumeSurveyUseCase,
                calculateMinAndMaxPriceOutOfStringUseCase,
                surveyRepository
            )

        }
    }

    @Test
    fun `test_getSurveyQuestions_checkIfInitializedStates`() = coroutineRule.runTest {
        launch { viewmodel.getSurveyResult() }.join()
        println("기다림 완료")
        assertEquals(perfumeSurveyQuestions.data, viewmodel.perfumeSurveyContentsState.value)
        assertEquals(perfumeSurveyQuestions.data?.noteCategoryTags, viewmodel.noteCategoryTagsState.value)
        assertEquals(listOf(false, false), viewmodel.isNextButtonAvailableState.value)
    }

    @Test
    fun `test_addOnePriceOption_UnAvailableMultiOption_reflectInPriceOptionIdsList`() = coroutineRule.runTest {
        val result = viewmodel.addNewPriceOption(0, false, listOf())
        assertEquals(listOf(0), result)
    }

    @Test
    fun `test_addTwoPriceOption_AvailableMultiOption_reflectInPriceOptionIdsList`() = coroutineRule.runTest {
        val afterAddOnePrice = viewmodel.addNewPriceOption(0, true, listOf())
        val afterAddTwoPrice = viewmodel.addNewPriceOption(1, true, afterAddOnePrice)
        assertEquals(listOf(0, 1), afterAddTwoPrice)
    }

    @Test
    fun `test_substractOnePriceOption_UnAvailableMultiOption_reflectInPriceOptionIdsList`() = coroutineRule.runTest {
        val expectedPriceOptionIds = emptyList<Int>()
        val afterAddOnePrice = viewmodel.addNewPriceOption(0, false, listOf())
        val afterSubtractOnePrice = viewmodel.substractPriceOption(0, false, afterAddOnePrice)
        assertEquals(expectedPriceOptionIds, afterSubtractOnePrice)
    }

    @Test
    fun `test_substractPriceOption_AvailableMultiOption_reflectInPriceOptionIdsList`() = coroutineRule.runTest {
        val afterAddOnePrice = viewmodel.addNewPriceOption(0, true, listOf())
        val afterAddTwoPrice = viewmodel.addNewPriceOption(1, true, afterAddOnePrice)
        val afterAddThreePrice = viewmodel.addNewPriceOption(2, true, afterAddTwoPrice)
        val afterSubtractOnePrice = viewmodel.substractPriceOption(0, true, afterAddThreePrice)
        val afterSubtractTwoPrice = viewmodel.substractPriceOption(1, true, afterSubtractOnePrice)
        assertEquals(listOf(2), afterSubtractTwoPrice)
    }

    @Test
    fun `test_handlePriceQuestionAnswer_addOnePriceOption_reflectInSelectedPriceOptionIdsState`() =
        coroutineRule.runTest {
            viewmodel.getSurveyResult()
            viewmodel.handlePriceQuestionAnswer(0, true)
            assertEquals(listOf(0), viewmodel.selectedPriceOptionIdsState.value)
        }

    @Test
    fun `test_handlePriceQuestionAnswer_substractOnePriceOption_reflectInSelectedPriceOptionIdsState`() =
        coroutineRule.runTest {
            viewmodel.getSurveyResult()
            viewmodel.handlePriceQuestionAnswer(0, true)
            viewmodel.handlePriceQuestionAnswer(1, true)
            viewmodel.handlePriceQuestionAnswer(0, false)
            assertEquals(listOf(1), viewmodel.selectedPriceOptionIdsState.value)
        }

    @Test
    fun `test_addOneNoteTag_checkFunctionReturn`() = coroutineRule.runTest {
        val note = noteCategoryTags[0].note[0] //라임 만다린
        viewmodel.getSurveyResult()
        val result = viewmodel.addNoteTagOption(note, listOf())
        assertEquals(listOf("라임 만다린"), result)
    }

    @Test
    fun `test_deleteOneNoteTag_checkFunctionReturn`() = coroutineRule.runTest {
        val note = noteCategoryTags[0].note[0] //라임 만다린
        val note2 = noteCategoryTags[0].note[1] //베르가뭇
        val afterAddOneNote = viewmodel.addNoteTagOption(note, listOf())
        val afterAddTwoNote = viewmodel.addNoteTagOption(note2, afterAddOneNote)
        val afterDeleteOneNote = viewmodel.deleteNoteTagOption(note2, afterAddTwoNote)
        assertEquals(listOf("라임 만다린"), afterDeleteOneNote)
    }

    @Test
    fun `test_deleteAllNoteTags_reflectInState`() = coroutineRule.runTest {
        val note = noteCategoryTags[0].note[0] //라임 만다린
        viewmodel.addNoteTagOption(note, listOf())
        viewmodel.deleteAllNoteTagOptions()
        assertEquals(emptyList<String>(), viewmodel.selectedNoteTagsOptionState.value)
    }

    @Test
    fun `test_handleNoteQuestionAnswer_addOneNote_reflectInSelectedNoteTagsOptionState`() =
        coroutineRule.runTest {
            val note = noteCategoryTags[0].note[0] //라임 만다린
            viewmodel.getSurveyResult()
            viewmodel.handleNoteQuestionAnswer(note = note, categoryIndex = 0, noteIndex = 0, isGotoState = true)
            assertEquals(
                listOf(
                    NoteCategoryTag(
                        category = noteCategoryTags[0].category,
                        note = noteCategoryTags[0].note,
                        isSelected = listOf(true, false, false, false)
                    ), noteCategoryTags[1], noteCategoryTags[2]
                ), viewmodel.noteCategoryTagsState.value
            )
        }

    @Test
    fun `test_handleNoteQuestionAnswer_addOneNoteAndDelete_reflectInSelectedNoteTagsOptionState`() =
        coroutineRule.runTest {
            val note = noteCategoryTags[0].note[0] //라임 만다린
            viewmodel.getSurveyResult()
            viewmodel.handleNoteQuestionAnswer(note = note, categoryIndex = 0, noteIndex = 0, isGotoState = true)
            viewmodel.onDeleteNoteTagOption(noteTag = note)
            assertEquals(
                listOf(
                    NoteCategoryTag(
                        category = noteCategoryTags[0].category,
                        note = noteCategoryTags[0].note,
                        isSelected = listOf(false, false, false, false)
                    ), noteCategoryTags[1], noteCategoryTags[2]
                ), viewmodel.noteCategoryTagsState.value
            )
            assertEquals(emptyList<Int>(), viewmodel.selectedPriceOptionIdsState.value)
        }

    @Test
    fun `test_handleNoteQuestionAnswer_addOneNoteAndCancel_reflectInSelectedNoteTagsOptionState`() =
        coroutineRule.runTest {
            val note = noteCategoryTags[0].note[0] //라임 만다린
            viewmodel.getSurveyResult()
            viewmodel.handleNoteQuestionAnswer(note = note, categoryIndex = 0, noteIndex = 0, isGotoState = true)
            viewmodel.handleNoteQuestionAnswer(note = note, categoryIndex = 0, noteIndex = 0, isGotoState = false)
            assertEquals(
                listOf(
                    NoteCategoryTag(
                        category = noteCategoryTags[0].category,
                        note = noteCategoryTags[0].note,
                        isSelected = listOf(false, false, false, false)
                    ), noteCategoryTags[1], noteCategoryTags[2]
                ), viewmodel.noteCategoryTagsState.value
            )
        }

    @Test
    fun `test_makePriceChoice_reflectInIsNextAvailableStateTrue`() =
        coroutineRule.runTest {
            viewmodel.getSurveyResult()
            viewmodel.handlePriceQuestionAnswer(optionIndex = 0, isGoToSelectedState = true)
            assertEquals(listOf(true, false), viewmodel.isNextButtonAvailableState.value)
        }

    @Test
    fun `test_makePriceAndNoteChoice_reflectInIsNextAvailableStateAllTrue`() =
        coroutineRule.runTest {
            val note = noteCategoryTags[0].note[0] //라임 만다린
            viewmodel.getSurveyResult()
            viewmodel.handlePriceQuestionAnswer(optionIndex = 0, isGoToSelectedState = true)
            viewmodel.handleNoteQuestionAnswer(note = note, categoryIndex = 0, noteIndex = 0, isGotoState = true)
            assertEquals(listOf(true, true), viewmodel.isNextButtonAvailableState.value)
        }

    @Test
    fun `test_mapOptionIdAndSelectedNoteTag_ToSurveyAnswerRequestDto`() = coroutineRule.runTest {
        Mockito.`when`(calculateMinAndMaxPriceOutOfStringUseCase.invoke("50,000 ~ 100,000원"))
            .thenReturn(Pair(50000, 100000))
        val expectedSurveyAnswerRequestDto =
            PerfumeSurveyAnswerRequestDto(maxPrice = 100000, minPrice = 50000, notes = listOf("라임 만다린"))
        viewmodel.getSurveyResult()
        viewmodel.handlePriceQuestionAnswer(1, true) //50,000원 이하 100,000원 이상
        viewmodel.handleNoteQuestionAnswer(
            note = noteCategoryTags[0].note[0],
            categoryIndex = 0,
            noteIndex = 0,
            isGotoState = true
        ) //라임 만다린
//        viewmodel.handleNoteQuestionAnswer(
//            note = noteCategoryTags[0].note[1],
//            categoryIndex = 0,
//            noteIndex = 0,
//            isGotoState = true
//        ) //베르가뭇
        val result = viewmodel.mapOptionIdAndNoteTagsToChangePostSurveyAnswer(
            selectedPriceOptionIds = viewmodel.selectedPriceOptionIdsState.value,
            perfumeSurveyContents = viewmodel.perfumeSurveyContentsState.value
        )
        assertEquals(expectedSurveyAnswerRequestDto, result)
    }
}
