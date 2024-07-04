package com.hmoa.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R

//Data가 없는 화면
@Composable
fun NoDataPage(
    mainMsg: String,
    subMsg: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Spacer(Modifier.height(66.dp))

        Text(
            text = mainMsg,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontSize = 30.sp
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = subMsg,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun TestNoDataPage() {
    NoDataPage(
        mainMsg = "좋아요를 누른 댓글이\n없습니다",
        subMsg = "좋아하는 함수에 좋아요를 눌러주세요"
    )
}