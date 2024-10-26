package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun Button(
    isEnabled: Boolean, //버튼 활성화 여부
    btnText: String, //버튼 메세지
    onClick: () -> Unit, //버튼 이벤트
    buttonModifier: Modifier? = null,
    textColor: Color = Color.White,
    textSize: Int = 20,
    radious: Int? = 0,
) {
    val roundCorner = if (radious != null) radious else 0
    Row(
        modifier = Modifier
            .background(
                color = if (isEnabled) Color.Black else CustomColor.gray2,
                shape = RoundedCornerShape(
                    topStart = roundCorner.dp,
                    topEnd = roundCorner.dp,
                    bottomStart = roundCorner.dp,
                    bottomEnd = roundCorner.dp
                )
            )
            .clickable {
                if (isEnabled) {
                    onClick()
                }
            }.clip(RoundedCornerShape(size = roundCorner.dp)).addModifier(buttonModifier).border(
                width = 1.dp, color = Color.Transparent, shape = RoundedCornerShape(
                    topStart = roundCorner.dp,
                    topEnd = roundCorner.dp,
                    bottomStart = roundCorner.dp,
                    bottomEnd = roundCorner.dp
                )
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = btnText,
            fontSize = textSize.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            color = textColor,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun TestBottomButton() {

    var text = "init"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            isEnabled = false,
            btnText = "다음",
            onClick = {
                text = "btn clicked"
            },
            radious = 20,
            textSize = 18,
            buttonModifier = Modifier.fillMaxWidth(0.9f).height(40.dp)
        )

        Spacer(Modifier.height(50.dp))

        Text(
            text = text,
            fontSize = 30.sp
        )
    }
}