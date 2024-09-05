package com.hmoa.feature_hbti

import ResultResponse
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hmoa.core_common.ErrorUiState
import com.hmoa.core_domain.repository.MemberRepository
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.request.NoteResponseDto
import com.hmoa.core_model.response.MemberResponseDto
import com.hmoa.feature_hbti.screen.HbtiSurveyResultScreen
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyResultUiState
import com.hmoa.feature_hbti.viewmodel.HbtiSurveyResultViewmodel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HbtiSurveyResultScreenTest : TestCase() {

    @get:Rule
    val composeTestRule = createComposeRule()
    val memberRepository = mock(MemberRepository::class.java)
    val surveyRepository = mock(SurveyRepository::class.java)
    lateinit var viewModel: HbtiSurveyResultViewmodel

    companion object {
        val fakeMember = ResultResponse<MemberResponseDto>(
            data = MemberResponseDto(
                age = 20,
                memberId = 0,
                memberImageUrl = "fakeUrl",
                nickname = "테스터",
                provider = "GOOGLE",
                sex = true
            ),
            errorMessage = null
        )
        val fakeSurveyResult = listOf(
            NoteResponseDto(content = "스파이스 어쩌구는 뭐뭐다", noteName = "스파이스", noteId = 0, notePhotoUrl = "spiceUrl"),
            NoteResponseDto(content = "그린 어쩌구는 뭐뭐다", noteName = "그린", noteId = 1, notePhotoUrl = "greenUrl"),
            NoteResponseDto(content = "머스크 어쩌구는 뭐뭐다", noteName = "머스크", noteId = 2, notePhotoUrl = "muskUrl")
        )
    }

    @Before
    public override fun setUp() {
        runBlocking {
            Mockito.`when`(memberRepository.getMember()).thenReturn(fakeMember)
            Mockito.`when`(surveyRepository.getAllSurveyResult()).thenReturn(fakeSurveyResult)
            viewModel = HbtiSurveyResultViewmodel(memberRepository, surveyRepository)
        }
    }

    @Test
    fun testLoadingState() {
        composeTestRule.setContent {
            HbtiSurveyResultScreen(
                onErrorHandleLoginAgain = {},
                onBackClick = {},
                onHbtiProcessClick = {},
                uiState = HbtiSurveyResultUiState.Loading,
                errorUiState = ErrorUiState.Loading,
            )
        }
        // Assert
        composeTestRule.onNodeWithTag("HbtiSurveyResultLoading").assertExists()
    }
}