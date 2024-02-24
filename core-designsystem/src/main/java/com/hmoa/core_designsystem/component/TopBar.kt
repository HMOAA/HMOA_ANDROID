package com.hmoa.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    navSize : Dp = 20.dp,
    navIcon : ImageVector?, //navigation 버튼
    onNavClick : () -> Unit = {}, //click 이벤트
    title : String //메인 타이틀
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        if (navIcon != null){
            IconButton(
                modifier = Modifier.size(navSize),
                onClick = onNavClick
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = navIcon,
                    contentDescription = "Navigation Button"
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = title,
            fontSize = 20.sp
        )

        Spacer(Modifier.weight(1f))

        if (navIcon != null) {
            Spacer(Modifier.width(navSize))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestTopBar(){
    TopBar(
        navIcon = Icons.Filled.KeyboardArrowLeft,
        title = "마이페이지"
    )
}