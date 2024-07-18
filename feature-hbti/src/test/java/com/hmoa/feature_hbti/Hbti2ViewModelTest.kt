package com.hmoa.feature_hbti

import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.feature_hbti.viewmodel.SelectSpiceViewModel
import junit.framework.TestCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class HbtiViewModelTest: TestCase() {
    @get:Rule(order = 0)
    val coroutineRule = TestCoroutineRule()

    private val surveyRepository = mock(SurveyRepository::class.java)
    lateinit var viewModel : SelectSpiceViewModel

    @Before
    override fun setUp() {
        runBlocking{
            viewModel = SelectSpiceViewModel(surveyRepository)
            viewModel.addTag("A")
            viewModel.addTag("B")
            viewModel.addTag("C")
        }
    }

    @Test
    fun 태그_선택_테스트() = coroutineRule.runTest{
        //추가
        viewModel.addTag("D")
        //결과
        Assert.assertEquals(listOf("A","B","C","D"), viewModel.selectedSpices.value)
    }
    @Test
    fun `태그_삭제_테스트`() = coroutineRule.runTest{
        //삭제
        viewModel.deleteTag("B")

        //결과
        Assert.assertEquals(listOf("A","C","D"), viewModel.selectedSpices.value)
    }
    @Test
    fun `태그_전체_삭제_테스트`() = coroutineRule.runTest {
        //전체 삭제
        viewModel.deleteAllTags()
        //결과
        Assert.assertEquals(listOf<String>(), viewModel.selectedSpices.value)
    }
    @Test
    fun `버튼_활성화_여부_테스트`() = coroutineRule.runTest{
        viewModel.isEnabledBtn.map{
            //list not empty일 경우
            Assert.assertEquals(false, it)
            viewModel.deleteAllTags()
            //list empty일 경우
            Assert.assertEquals(true, it)
        }
    }
}