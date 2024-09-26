package com.hmoa.feature_userinfo.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun MyInfoRoute(
    navBack : () -> Unit,
    navMyBirth : () -> Unit,
    navMyGender : () -> Unit,
){
    MyInfoPage(
        navBack = navBack,
        navMyBirth = navMyBirth,
        navMyGender = navMyGender
    )
}

@Composable
fun MyInfoPage(
    navBack : () -> Unit,
    navMyBirth : () -> Unit,
    navMyGender : () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
            onNavClick = navBack, //뒤오 가기
            title = "내 정보"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "출생연도",
                fontFamily = FontFamily(Font(com.hmoa.core_designsystem.R.font.pretendard_regular)),
                fontSize = 16.sp,
            )
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = navMyBirth
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_next),
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
        ){
            Text(
                text = "성별",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            )
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = navMyGender
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_next),
                    contentDescription = "Navigation Button",
                    tint = CustomColor.gray2
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = CustomColor.gray2)
    }
}