package com.hmoa.feature_hbti

import androidx.compose.ui.test.junit4.createComposeRule
import com.hmoa.feature_hbti.screen.PerfumeCommentResultContent
import org.junit.Rule
import org.junit.Test

class PerfumeRecommendationResultScreenTest {
    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Test
    fun test_EmptyPriceRecommendation_ShowsEmptyResultInfoScreen() {
        composeTestRule.setContent {
            PerfumeCommentResultContent(
                perfumes = emptyList(),
                isPriceSortedSelected = true,
                isNoteSortedSelected = false,
                isEmptyPriceContentNeedState = true,
                onClickButton = {},
                onClickPriceSorted = {},
                onClickNoteSorted = {},
                navBack = {},
                navPerfume = {}
            )
        }
        Thread.sleep(4000)
    }

}
