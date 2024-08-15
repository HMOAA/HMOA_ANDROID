package com.hmoa.feature_hbti

import ResultResponse
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.data.HbtiQuestionItem
import com.hmoa.core_model.data.HbtiQuestionItems
import com.hmoa.core_model.response.SurveyOptionResponseDto
import com.hmoa.core_model.response.SurveyQuestionResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyViewmodel
import junit.framework.TestCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class HbitSurveyViewModelTest : TestCase() {
    @get:Rule(order = 0)
    val coroutineRule = TestCoroutineRule()

    private val surveyRepository = mock(SurveyRepository::class.java)
    lateinit var viewModel: HbtiSurveyViewmodel
    lateinit var hbtiQuestionItem_singleChoice: HbtiQuestionItem
    lateinit var hbtiQuestionItem_multiChoice: HbtiQuestionItem

    companion object {
        //0번째 질문, page = 0
        val fakeQuestion1 = SurveyQuestionResponseDto(
            answers = listOf(
                SurveyOptionResponseDto(option = "봄", optionId = 0),
                SurveyOptionResponseDto(option = "여름", optionId = 1),
                SurveyOptionResponseDto(option = "가을", optionId = 2),
                SurveyOptionResponseDto(option = "겨울", optionId = 3)
            ), content = "좋아하는 계절이 무엇입니까?", questionId = 0, isMultipleChoice = false
        )

        //1번째 질문, page = 1
        val fakeQuestion2 = SurveyQuestionResponseDto(
            answers = listOf(
                SurveyOptionResponseDto(option = "도시", optionId = 0),
                SurveyOptionResponseDto(option = "숲", optionId = 1),
                SurveyOptionResponseDto(option = "바다", optionId = 2),
                SurveyOptionResponseDto(option = "호수", optionId = 3)
            ), content = "좋아하는 여행지는 무엇입니까?", questionId = 1, isMultipleChoice = true
        )
        val fakeQuestions = listOf(fakeQuestion1, fakeQuestion2)
        val fakeResponse = ResultResponse<SurveyQuestionsResponseDto>(
            SurveyQuestionsResponseDto(
                questions = fakeQuestions,
                title = "hbti테스트 질문"
            )
        )
    }


    @Before
    override fun setUp() {
        hbtiQuestionItem_singleChoice = HbtiQuestionItem(
            questionId = fakeQuestion1.questionId,
            questionContent = fakeQuestion1.content,
            optionIds = fakeQuestion1.answers.map { it.optionId },
            optionContents = fakeQuestion1.answers.map { it.option },
            isMultipleChoice = fakeQuestion1.isMultipleChoice,
            selectedOptionIds = mutableListOf()
        )
        hbtiQuestionItem_multiChoice = HbtiQuestionItem(
            questionId = fakeQuestion2.questionId,
            questionContent = fakeQuestion2.content,
            optionIds = fakeQuestion2.answers.map { it.optionId },
            optionContents = fakeQuestion2.answers.map { it.option },
            isMultipleChoice = fakeQuestion2.isMultipleChoice,
            selectedOptionIds = mutableListOf()
        )
        runBlocking {
            Mockito.`when`(surveyRepository.getSurveyQuestions()).thenReturn(fakeResponse)
            viewModel = spy(HbtiSurveyViewmodel(surveyRepository)) //내부 메서드 호출 감지 가능
            println("setUp")
        }
    }

    @Test
    fun `test_getSurveyQuestions 요청 성공 후 hbtiQuestionItems의 초기화된 상태값 확인`() = coroutineRule.runTest {
        val expectedValue = HbtiQuestionItems(
            hbtiQuestions = mutableMapOf(
                0 to hbtiQuestionItem_singleChoice,
                1 to hbtiQuestionItem_multiChoice
            )
        )
        launch { viewModel.getSurveyQuestions() }.join()
        assertEquals(expectedValue, viewModel.hbtiQuestionItemsState.value)
    }

    @Test
    fun `test_getSurveyQuestions 요청 성공 후 hbtiQuestionItems 초기화 함수 호출 여부 확인`() = coroutineRule.runTest {
        launch { viewModel.getSurveyQuestions() }.join()
        verify(viewModel).initializeHbtiQuestionItemsState(fakeResponse.data)
    }

    @Test
    fun `test_단일 선택인 질문 답안을 처음 선택한 경우, 정답으로 업데이트 되었는지 확인`() = coroutineRule.runTest {
        val expectedValue = mutableListOf(hbtiQuestionItem_singleChoice.optionIds[0])
        val result = viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_singleChoice.optionIds[0],
            hbtiQuestionItem_singleChoice.isMultipleChoice,
            hbtiQuestionItem_singleChoice.selectedOptionIds
        )
        assertEquals(expectedValue, result)
    }

    @Test
    fun `test_단일 선택인 질문의 정답을 바꾸는 경우, 기존 정답이 없어지고 새로운 정답으로 업데이트 되는지 확인`() = coroutineRule.runTest {
        val expectedOldValue = mutableListOf(hbtiQuestionItem_singleChoice.optionIds[0])
        val expectedNewValue = mutableListOf(hbtiQuestionItem_singleChoice.optionIds[1])
        //기존 정답 입력
        val oldResult = viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_singleChoice.optionIds[0],
            hbtiQuestionItem_singleChoice.isMultipleChoice,
            hbtiQuestionItem_singleChoice.selectedOptionIds
        )
        assertEquals(expectedOldValue, oldResult)
        //새로운 정답 입력
        val newResult = viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_singleChoice.optionIds[1],
            hbtiQuestionItem_singleChoice.isMultipleChoice,
            hbtiQuestionItem_singleChoice.selectedOptionIds
        )
        assertEquals(expectedNewValue, newResult)
    }

    @Test
    fun `test_복수 선택인 질문 답안을 처음 선택한 경우, 정답으로 업데이트 되었는지 확인`() = coroutineRule.runTest {
        val expectedValue =
            mutableListOf(hbtiQuestionItem_multiChoice.optionIds[0], hbtiQuestionItem_multiChoice.optionIds[1])
        viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[0],
            hbtiQuestionItem_multiChoice.isMultipleChoice,
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        val result = viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[1],
            hbtiQuestionItem_multiChoice.isMultipleChoice,
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        assertEquals(expectedValue, result)
    }

    @Test
    fun `test_복수 선택인 질문의 정답을 추가하는 경우, 기존 정답 뒤에 새로운 정답이 추가되는지 확인`() = coroutineRule.runTest {
        val expectedValue =
            mutableListOf(hbtiQuestionItem_multiChoice.optionIds[0], hbtiQuestionItem_multiChoice.optionIds[1])
        viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[0],
            hbtiQuestionItem_multiChoice.isMultipleChoice,
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        val result = viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[1],
            hbtiQuestionItem_multiChoice.isMultipleChoice,
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        assertEquals(expectedValue, result)
    }

    @Test
    fun `test_단수 선택인 질문의 정답을 삭제하는 경우, 정답 리스트가 비어있는지 확인`() = coroutineRule.runTest {
        viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[0],
            hbtiQuestionItem_multiChoice.isMultipleChoice,
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        val result = viewModel.decreaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[0],
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        assert(result.isEmpty())
    }

    @Test
    fun `test_복수 선택인 질문의 정답을 변경하는 경우,정답 2개 중 두번째를 삭제하고 새로운 정답을 추가했을 때 결과 확인`() = coroutineRule.runTest {
        val expectedValue =
            mutableListOf(hbtiQuestionItem_multiChoice.optionIds[0], hbtiQuestionItem_multiChoice.optionIds[2])
        viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[0],
            hbtiQuestionItem_multiChoice.isMultipleChoice,
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[1],
            hbtiQuestionItem_multiChoice.isMultipleChoice,
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        viewModel.decreaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[1],
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        val result = viewModel.increaseHbtiQuestionItem_SelectedOption(
            hbtiQuestionItem_multiChoice.optionIds[2],
            hbtiQuestionItem_multiChoice.isMultipleChoice,
            hbtiQuestionItem_multiChoice.selectedOptionIds
        )
        assertEquals(expectedValue, result)
    }

    @Test
    fun `test_getUpdatedHbtiQuestionItems, 수정된 답안이 HbtiQuestionItems의 page인덱스에 정확히 삽입되는지 확인 `() =
        coroutineRule.runTest {
            val updatedItem = HbtiQuestionItem(
                questionId = hbtiQuestionItem_multiChoice.questionId,
                questionContent = hbtiQuestionItem_multiChoice.questionContent,
                optionIds = hbtiQuestionItem_multiChoice.optionIds,
                optionContents = hbtiQuestionItem_multiChoice.optionContents,
                isMultipleChoice = hbtiQuestionItem_multiChoice.isMultipleChoice,
                selectedOptionIds = mutableListOf(
                    hbtiQuestionItem_multiChoice.optionIds[0],
                    hbtiQuestionItem_multiChoice.optionIds[1]
                )
            )
            val expectedValue =
                HbtiQuestionItems(hbtiQuestions = mutableMapOf(0 to hbtiQuestionItem_singleChoice, 1 to updatedItem))

            viewModel.getSurveyQuestions()
            val result = viewModel.getUpdatedHbtiQuestionItems(page = 1, newHbtiQuestionItem = updatedItem)
            assertEquals(expectedValue, result)
        }

    @Test
    fun `test_복수 선택인 질문 1개에 대해 정답을 2개를 선택했을 경우, hbtiQuestioinItemsState가 이를 반영했는지 상태값 확인`() = coroutineRule.runTest {
        val expectedValue = HbtiQuestionItems(
            hbtiQuestions = mutableMapOf(
                0 to hbtiQuestionItem_singleChoice,
                1 to HbtiQuestionItem(
                    questionId = hbtiQuestionItem_multiChoice.questionId,
                    questionContent = hbtiQuestionItem_multiChoice.questionContent,
                    optionIds = hbtiQuestionItem_multiChoice.optionIds,
                    optionContents = hbtiQuestionItem_multiChoice.optionContents,
                    isMultipleChoice = hbtiQuestionItem_multiChoice.isMultipleChoice,
                    selectedOptionIds = mutableListOf(
                        hbtiQuestionItem_multiChoice.optionIds[0],
                        hbtiQuestionItem_multiChoice.optionIds[1]
                    )
                )
            )
        )
        viewModel.getSurveyQuestions()
        viewModel.modifyAnswersToOptionId(
            page = 1,
            optionId = hbtiQuestionItem_multiChoice.optionIds[0],
            currentHbtiQuestionItem = hbtiQuestionItem_multiChoice,
            isGoToSelectedState = true
        )
        viewModel.modifyAnswersToOptionId(
            page = 1,
            optionId = hbtiQuestionItem_multiChoice.optionIds[1],
            currentHbtiQuestionItem = hbtiQuestionItem_multiChoice,
            isGoToSelectedState = true
        )
        assertEquals(expectedValue, viewModel.hbtiQuestionItemsState.value)

    }
}