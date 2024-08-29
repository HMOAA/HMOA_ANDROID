package com.hmoa.feature_hbti

import ResultResponse
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hmoa.core_common.ErrorMessageType
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.data.ErrorMessage
import com.hmoa.core_model.data.HbtiQuestionItem
import com.hmoa.core_model.data.HbtiQuestionItems
import com.hmoa.core_model.request.SurveyRespondRequestDto
import com.hmoa.core_model.response.RecommendNotesResponseDto
import com.hmoa.feature_hbti.screen.HbtiSurveyScreen
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyUiState
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyViewmodel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class HbtiSurveyScreenTest : TestCase() {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    val surveyRepository = mock(SurveyRepository::class.java)
    lateinit var viewModel: HbtiSurveyViewmodel

    companion object {
        val fakeSurveyRespond = SurveyRespondRequestDto(mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
        val unknownError = ResultResponse<RecommendNotesResponseDto>(
            data = null,
            errorMessage = ErrorMessage(
                code = ErrorMessageType.UNKNOWN_ERROR.code.toString(),
                message = ErrorMessageType.UNKNOWN_ERROR.message
            )
        )
        val hbtiQuestionItem_singleChoice = HbtiQuestionItem(
            questionId = 0,
            questionContent = "좋아하는 계절이 무엇입니까?",
            optionIds = listOf(0, 1, 2, 3),
            optionContents = listOf("봄", "여름", "가을", "겨울"),
            isMultipleChoice = false,
            selectedOptionIds = mutableListOf()
        )
        val hbtiData = HbtiSurveyUiState.HbtiData(
            hbtiQuestionItems = HbtiQuestionItems(mutableMapOf(0 to hbtiQuestionItem_singleChoice)),
            hbtiAnswerIds = null
        )
    }

    @Before
    public override fun setUp() {
        runBlocking {
            viewModel = HbtiSurveyViewmodel(surveyRepository)
        }
    }

    @Test
    fun test_getSurveyQuestions_SurveyItemsExist() {
        composeTestRule.setContent {
            HbtiSurveyScreen(
                onErrorHandleLoginAgain = {},
                onBackClick = {},
                onClickFinishSurvey = {},
                viewModel = viewModel,
                errorUiState = ErrorUiState.Loading,
                uiState = hbtiData
            )
        }
    }

    @Test
    fun test_unKnownError_ErrorUiSetViewExist() {
        composeTestRule.setContent {
            HbtiSurveyScreen(
                onErrorHandleLoginAgain = {},
                onBackClick = {},
                onClickFinishSurvey = {},
                viewModel = viewModel,
                errorUiState = ErrorUiState.ErrorData(
                    expiredTokenError = false,
                    wrongTypeTokenError = false,
                    unknownError = true,
                    generalError = Pair(false, "원인불명 에러")
                ),
                uiState = hbtiData
            )
        }
        composeTestRule.onNodeWithTag("unknownError").assertIsDisplayed()
    }
}