package com.example.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyangmoa.component.TopBar
import com.hyangmoa.core_designsystem.theme.CustomColor
import com.hyangmoa.feature_userinfo.R

@Composable
fun MyInfoRoute(
    onNavBack: () -> Unit,
    onNavMyBirth: () -> Unit,
    onNavMyGender: () -> Unit,
) {
    MyInfoPage(
        onNavBack = onNavBack,
        onNavMyBirth = onNavMyBirth,
        onNavMyGender = onNavMyGender
    )
}

@Composable
fun MyInfoPage(
    onNavBack: () -> Unit,
    onNavMyBirth: () -> Unit,
    onNavMyGender: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopBar(
            navIcon = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = onNavBack, //뒤오 가기
            title = "내 정보"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "출생연도",
                fontSize = 16.sp,
            )
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onNavMyBirth
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_next),
                    contentDescription = "Navigation Button",
                    tint = CustomColor.gray2
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "성별", fontSize = 16.sp)
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onNavMyGender
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(com.hyangmoa.core_designsystem.R.drawable.ic_next),
                    contentDescription = "Navigation Button",
                    tint = CustomColor.gray2
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
    }
}