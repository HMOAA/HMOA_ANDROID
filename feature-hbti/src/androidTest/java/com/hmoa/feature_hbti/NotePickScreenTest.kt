package com.hmoa.feature_hbti

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hmoa.core_domain.repository.HshopRepository
import com.hmoa.core_domain.repository.SurveyRepository
import com.hmoa.core_model.data.NoteSelect
import com.hmoa.core_model.response.ProductListResponseDto
import com.hmoa.core_model.response.ProductResponseDto
import com.hmoa.feature_hbti.screen.NotePickGridWindow
import com.hmoa.feature_hbti.viewmodel.NotePickViewmodel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class NotePickScreenTest : TestCase() {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    val surveyRepository = mock(SurveyRepository::class.java)
    val hshopRepository = mock(HshopRepository::class.java)
    lateinit var viewmodel: NotePickViewmodel

    companion object {
        //0,1,2번째는 추천향료
        val notes = ProductListResponseDto(
            listOf(
                ProductResponseDto(
                    productId = 0,
                    productName = "우드",
                    productDetails = "나무 어쩌고저쩌고",
                    productPhotoUrl = "나무사진",
                    isRecommended = true
                ),
                ProductResponseDto(
                    productId = 1,
                    productName = "프루트",
                    productDetails = "피치,블루베리,블랙체리",
                    productPhotoUrl = "과일사진",
                    isRecommended = true
                ),
                ProductResponseDto(
                    productId = 2,
                    productName = "아쿠아",
                    productDetails = "씨 솔트",
                    productPhotoUrl = "바다사진",
                    isRecommended = true
                ),
                ProductResponseDto(
                    productId = 3,
                    productName = "스위트",
                    productDetails = "허니, 바닐라, 프랄린",
                    productPhotoUrl = "꿀사진",
                    isRecommended = false
                ),
                ProductResponseDto(
                    productId = 4,
                    productName = "스파이스",
                    productDetails = "넛맥, 블랙페퍼",
                    productPhotoUrl = "후추사진",
                    isRecommended = false
                ),
                ProductResponseDto(
                    productId = 5,
                    productName = "머스크",
                    productDetails = "화이트 머스크, 코튼, 엠버, 벤조인",
                    productPhotoUrl = "카펫사진",
                    isRecommended = false
                )
            )
        )
    }

    @Before
    public override fun setUp() {
        super.setUp()
        runBlocking {
            //viewmodel = NotePickViewmodel(surveyRepository, hshopRepository)
        }
    }

    @Test
    fun test_Selecting3OutOf5Notes__ShowsSelectionCorrectly() {
        var selectedIndex = 0
        val isNoteSelectedList = notes.data.mapIndexed { index, item ->

            NoteSelect(
                productId = item.productId,
                isSelected = if (index in listOf(0, 2, 4)) {
                    selectedIndex += 1
                    true
                } else false, //0(추천향료),2(추천향료),4번째 원소가 선택된 상황
                nodeFaceIndex = selectedIndex,
                isRecommended = item.isRecommended
            )
        }
        composeTestRule.setContent {
            NotePickGridWindow(
                notes = notes,
                noteOrderQuantity = 5,
                selectedNotesOrderQuantity = 3,
                isNoteSelectedList = isNoteSelectedList,
                onClickItem = { index, value, data, noteOrderQuantity, selectedNotesOrderQuantity -> }
            )
        }
        composeTestRule.onAllNodesWithText("Best!")[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Best!")[1].assertIsDisplayed()
        composeTestRule.onNodeWithText("3").assertIsDisplayed()
    }
}