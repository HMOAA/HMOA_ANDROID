package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun VerticalStepBar(titles: Array<String>, contents: Array<String>) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
        titles.forEachIndexed { index, s ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .size(20.dp)
                        .background(color = CustomColor.gray6, shape = CircleShape)
                        .border(
                            width = 1.dp,
                            color = CustomColor.gray6,
                            shape = CircleShape,
                        ),
                ) {
                    Text(
                        (index + 1).toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Text(
                    titles[index],
                    style = TextStyle(fontSize = 14.sp, fontFamily = pretendard, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
            Row(modifier = Modifier.padding(start = 10.dp)) {
                if (titles.size - 1 > index) {
                    VerticalDivider(modifier = Modifier.border(width = 1.dp, color = CustomColor.gray6).height(60.dp))
                }
                Text(
                    contents[index],
                    style = TextStyle(fontSize = 12.sp, color = CustomColor.gray5,fontFamily = pretendard, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(start = 22.dp, top = 6.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun VerticalStepBarPreview() {
    VerticalStepBar(
        arrayOf("향료 선택", "배송", "향수 추천"),
        arrayOf("향BTI 검사 이후 추천하는 향료, 원하는 향료 선택(가격대 상이)", "결제 후 1~2일 내 배송 완료", "시향 후 가장 좋았던 향료 선택, 향수 추천 받기")
    )
}