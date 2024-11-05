package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.component.TopBar
import com.hmoa.core_designsystem.component.VerticalStepBar

@Composable
fun HbtiProcessRoute(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    HbtiProcessScreen(
        onBackClick = { onBackClick() },
        onNextClick = { onNextClick() })
}

@Composable
private fun HbtiProcessScreen(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White).padding(bottom = 40.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            TopBar(
                title = "향BTI",
                titleColor = Color.Black,
                navIcon = painterResource(R.drawable.ic_back),
                onNavClick = { onBackClick() }
            )
            Column(modifier = Modifier.padding(top = 22.dp).padding(horizontal = 16.dp)) {
                VerticalStepBar(
                    arrayOf("향료 선택", "배송", "향수 추천"),
                    arrayOf("향BTI 검사 이후 추천하는 향료, 원하는 향료 선택(가격대 상이)", "결제 후 1~2일 내 배송 완료", "시향 후 가장 좋았던 향료 선택, 향수 추천 받기")
                )
            }
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Button(
                isEnabled = true,
                btnText = "다음",
                onClick = { onNextClick() },
                buttonModifier = Modifier.fillMaxWidth(1f).height(52.dp).background(color = Color.Black),
                textSize = 18,
                textColor = Color.White,
                radious = 5
            )
        }
    }
}

@Preview
@Composable
private fun HbtiProcessScreenPreview() {
    HbtiProcessScreen(onBackClick = {}, onNextClick = {})
}