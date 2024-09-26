package com.hmoa.feature_like.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.component.TopBar

@Composable
fun NoSavePerfumeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(title = "저장")
        Spacer(Modifier.height(57.dp))
        Icon(
            modifier = Modifier.size(110.dp),
            painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_app_default_1),
            contentDescription = "App Icon"
        )
        Spacer(Modifier.height(34.dp))
        Text(
            text = "HMOA",
            fontSize = 9.sp,
            color = Color.Black,
            letterSpacing = 9.sp
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "지정된 향수가 없습니다",
            fontSize = 24.sp,
            color = Color.Black,
        )
        Spacer(Modifier.height(62.dp))
        Text(
            text = "좋아하는 향수를 저장해주세요",
            fontSize = 16.sp,
            color = Color.Black,
        )
    }
}

@Preview
@Composable
fun TestNoDataPerfumePage(){
    NoSavePerfumeScreen()
}