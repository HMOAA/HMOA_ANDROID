package com.hyangmoa.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyangmoa.core_designsystem.theme.CustomColor

//인증이 안되어 있는 My Page
@Composable
fun NoAuthMyPage(
    onNavLogin: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(126.dp))

        Text(
            text = "로그인이 필요한\n페이지 입니다",
            fontSize = 30.sp,
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "향모아의 회원이 되면\n더 많은 기능을 사용할 수 있어요",
            fontSize = 16.sp,
        )

        Spacer(Modifier.height(38.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "로그인 바로가기",
                fontSize = 16.sp
            )

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onNavLogin
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_next),
                    contentDescription = "Nav Button",
                    tint = CustomColor.gray2
                )
            }
        }
    }
}

@Preview
@Composable
fun TestNoAuthMyPage() {
    NoAuthMyPage(
        onNavLogin = {}
    )
}