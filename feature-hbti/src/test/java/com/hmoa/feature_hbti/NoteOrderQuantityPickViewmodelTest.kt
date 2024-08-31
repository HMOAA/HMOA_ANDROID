package com.hmoa.feature_hbti

import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.feature_hbti.viewmodel.NoteOrderQuantityPickViewmodel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy

class NoteOrderQuantityPickViewmodelTest : TestCase() {
    @get:Rule(order = 0)
    val coroutineRule = TestCoroutineRule()
    private val surveyRepository = mock(SurveyRepository::class.java)
    lateinit var viewmodel: NoteOrderQuantityPickViewmodel

    companion object {
        val fakeSurveyResult = listOf(
            NoteResponseDto(
                content = "상큼한 과일향기",
                noteId = 4,
                noteName = "시트러스",
                notePhotoUrl = "fake_notePhotoUrl"
            )
        )
    }

    @Before
    override fun setUp() {
        super.setUp()
        runBlocking {
            Mockito.`when`(surveyRepository.getAllSurveyResult()).thenReturn(fakeSurveyResult)
        }
        viewmodel = spy(NoteOrderQuantityPickViewmodel(surveyRepository))
    }

    @Test
    fun `test_주문수량 선택지 업데이트 메소드_빈 배열에 새로운 값 추가_추가된 값 확인`() = coroutineRule.runTest {
        val expectedValue = listOf(0)
        val result = viewmodel.updateAnswerOption(emptyList(), 0)
        assertEquals(expectedValue, result)
    }

    @Test
    fun `test_주문수량 선택지 업데이트 메소드_기존 값을 새로운 값으로 교체_교체된 값 확인`() = coroutineRule.runTest {
        val expectedValue = listOf(1)
        val answerIds = emptyList<Int>()
        viewmodel.updateAnswerOption(answerIds, 0)
        val result = viewmodel.updateAnswerOption(answerIds, 1)
        assertEquals(expectedValue, result)
    }

    @Test
    fun `test_주문수량 선택_빈 배열에 새로운 값 추가_상태변수 값 확인`() = coroutineRule.runTest {
        val expectedValue = listOf(0)
        viewmodel.modifyAnswerOption(0, true)
        assertEquals(expectedValue, viewmodel.noteQuantityChoiceAnswersId.value)
    }

    @Test
    fun `test_주문수량 선택지 취소_정답배열이 빈 배열인지 확인`() = coroutineRule.runTest {
        val expectedValue = emptyList<Int>()
        viewmodel.modifyAnswerOption(0, false)
        assertEquals(expectedValue, viewmodel.noteQuantityChoiceAnswersId.value)
    }
}