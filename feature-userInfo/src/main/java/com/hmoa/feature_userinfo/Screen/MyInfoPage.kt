package com.example.feature_userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.feature_userinfo.R

@Composable
fun MyInfoRoute(
    onNavBack : () -> Unit,
    onNavMyBirth : () -> Unit,
    onNavMyGender : () -> Unit,
){
    MyInfoPage(
        onNavBack = onNavBack,
        onNavMyBirth = onNavMyBirth,
        onNavMyGender = onNavMyGender
    )
}

@Composable
fun MyInfoPage(
    onNavBack : () -> Unit,
    onNavMyBirth : () -> Unit,
    onNavMyGender : () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            navIcon = painterResource(com.hmoa.core_designsystem.R.drawable.ic_back),
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
        ){
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
            Text(text = "성별",fontSize = 16.sp)
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onNavMyGender
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