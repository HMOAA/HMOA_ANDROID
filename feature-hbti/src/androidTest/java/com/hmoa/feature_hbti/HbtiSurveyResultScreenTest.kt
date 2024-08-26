package com.hmoa.feature_hbti

import ResultResponse
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hmoa.core_model.response.MemberResponseDto
import com.hmoa.feature_hbti.screen.HbtiSurveyResultScreen
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyResultViewmodel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HbtiSurveyResultScreenTest : TestCase() {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()
    lateinit var viewModel: HbtiSurveyResultViewmodel

//    val memberId: Int,
//    val memberImageUrl: String,
//    val nickname: String,
//    val provider: String,
//    val sex: Boolean
    companion object{
        val fakeMember = ResultResponse<MemberResponseDto>(
            data = MemberResponseDto(age = 20, memberId = 0, memberImageUrl = "fakeUrl", nickname="tester", provider = "GOOGLE", sex = true),
            errorMessage = null
        )
        val fakeSurveyResult = ResultResponse<>
    }
    @Before
    override fun setUp() {
        runBlocking {
            Mockito.`when`(viewModel.getUserName()).thenReturn()
            Mockito.`when`(viewModel.getSurveyResult()).thenReturn()
        }
    }

    @Test
    fun testLoadingState() {
        composeTestRule.setContent {
            HbtiSurveyResultScreen(
                onErrorHandleLoginAgain = {},
                onBackClick = {},
                onHbtiProcessClick = {},
                viewmodel = FakeHbtiSurveyResultViewModel().apply {
                    setUiState(HbtiSurveyResultUiState.Loading)
                }
            )
        }

        composeTestRule.onNodeWithText("   ") // 로딩 화면에서의 텍스트
            .assertExists()
    }

    @Test
    fun testSurveyResultDataState() {
        composeTestRule.setContent {
            HbtiSurveyResultScreen(
                onErrorHandleLoginAgain = {},
                onBackClick = {},
                onHbtiProcessClick = {},
                viewmodel = FakeHbtiSurveyResultViewModel().apply {
                    setUiState(HbtiSurveyResultUiState.HbtiSurveyResultData("John Doe", "Sample Result"))
                }
            )
        }

        composeTestRule.onNodeWithText("John Doe")
            .assertExists()

        composeTestRule.onNodeWithText("Sample Result")
            .assertExists()
    }

    @Test
    fun testLoadingToContentTransition() {
        composeTestRule.setContent {
            HbtiSurveyResultScreen(
                onErrorHandleLoginAgain = {},
                onBackClick = {},
                onHbtiProcessClick = {},
                viewmodel = FakeHbtiSurveyResultViewModel().apply {
                    setUiState(HbtiSurveyResultUiState.HbtiSurveyResultData("John Doe", "Sample Result"))
                }
            )
        }

        composeTestRule.mainClock.advanceTimeBy(2000) // 2초 후 상태 변화

        composeTestRule.onNodeWithText("John Doe")
            .assertExists()
    }
}