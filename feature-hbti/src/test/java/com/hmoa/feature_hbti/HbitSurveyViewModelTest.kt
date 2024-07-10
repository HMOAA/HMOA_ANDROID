package com.hmoa.feature_hbti

import ResultResponse
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.response.SurveyOptionResponseDto
import com.hmoa.core_model.response.SurveyQuestionResponseDto
import com.hmoa.core_model.response.SurveyQuestionsResponseDto
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyViewmodel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class HbitSurveyViewModelTest : TestCase() {
    @get:Rule(order = 0)
    val coroutineRule = TestCoroutineRule()

    private val surveyRepository = mock(SurveyRepository::class.java)
    lateinit var viewModel: HbtiSurveyViewmodel

    val fakeQuestions = listOf(
        SurveyQuestionResponseDto(
            answers = listOf(
                SurveyOptionResponseDto(option = "봄", optionId = 0),
                SurveyOptionResponseDto(option = "여름", optionId = 1),
                SurveyOptionResponseDto(option = "가을", optionId = 2),
                SurveyOptionResponseDto(option = "겨울", optionId = 3)
            ), content = "좋아하는 계절이 무엇입니까?", questionId = 0
        ),
        SurveyQuestionResponseDto(
            answers = listOf(
                SurveyOptionResponseDto(option = "도시", optionId = 0),
                SurveyOptionResponseDto(option = "숲", optionId = 1),
                SurveyOptionResponseDto(option = "바다", optionId = 2),
                SurveyOptionResponseDto(option = "호수", optionId = 3)
            ), content = "좋아하는 여행지는 무엇입니까?", questionId = 0
        )
    )
    val fakeResponse = ResultResponse<SurveyQuestionsResponseDto>(
        SurveyQuestionsResponseDto(
            questions = fakeQuestions,
            title = "hbti테스트 질문"
        )
    )

    @Before
    override fun setUp() {
        runBlocking {
            Mockito.`when`(surveyRepository.getSurveyQuestions()).thenReturn(fakeResponse)
            viewModel = HbtiSurveyViewmodel(surveyRepository)
        }
    }

    @Test
    fun testUpdateQuestion() = coroutineRule.runTest {
        Assert.assertEquals(fakeQuestions.map { it.content }, viewModel.updateQuestionState(fakeResponse.data))
    }

    @Test
    fun testUpdateOptionContent() = coroutineRule.runTest {
        Assert.assertEquals(
            fakeQuestions.map { it.answers.map { it.option } },
            viewModel.updateOptionContentState(fakeResponse.data)
        )
    }

    @Test
    fun testUpdateOptions() = coroutineRule.runTest {
        Assert.assertEquals(fakeQuestions.map { it.answers }, viewModel.updateOptionsState(fakeResponse.data))
    }


    @Test
    fun testStateValueUpdateAfterGetSurveyQuestions() = coroutineRule.runTest {
        viewModel.getSurveyQuestions()
        val questionsResult = viewModel.questionsState.value
        val optionsContentResult = viewModel.optionsContentState.value
        val optionsResult = viewModel.optionsState.value
        Assert.assertEquals(fakeQuestions.map { it.content }, questionsResult)
        Assert.assertEquals(fakeQuestions.map { it.answers.map { it.option } }, optionsContentResult)
        Assert.assertEquals(fakeQuestions.map { it.answers }, optionsResult)
    }
}