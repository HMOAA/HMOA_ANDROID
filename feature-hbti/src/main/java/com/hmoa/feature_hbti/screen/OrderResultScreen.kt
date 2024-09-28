package com.hmoa.feature_hbti.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.hmoa.core_designsystem.component.Button
import com.hmoa.core_designsystem.theme.CustomFont

@Composable
fun OrderResultRoute(
    navBack: () -> Unit,
    navHome: () -> Unit
){
    OrderResultScreen(navBack, navHome)
}

@Composable
fun OrderResultScreen(
    navBack: () -> Unit,
    navHome: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(bottom = 40.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            title = "결제완료",
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = navBack
        )
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier.size(110.dp),
            painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_app_default_1),
            contentDescription = "App Default Icon"
        )
        Spacer(Modifier.height(34.dp))
        Text(
            text = "결제가 완료 되었습니다.",
            fontSize = 22.sp,
            fontFamily = CustomFont.bold,
        )
        Spacer(Modifier.weight(1f))
        Button(
            buttonModifier = Modifier.fillMaxWidth().height(52.dp),
            radious = 5,
            isEnabled = true, 
            btnText = "홈으로 돌아가기",
            onClick = navHome
        )
    }
}

@Preview
@Composable
private fun OrderResultUITest(){
    OrderResultScreen(
        navBack = {},
        navHome = {}
    )
}