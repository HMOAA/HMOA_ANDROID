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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    iconSize : Dp = 20.dp, //icon 크기
    navIcon : Painter ?= null, //navigation 버튼
    onNavClick : () -> Unit = {}, //navigation click 이벤트
    menuIcon : Painter ?= null, //menu 버튼
    onMenuClick : () -> Unit = {}, //menu click 이벤트
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
                modifier = Modifier.size(iconSize),
                onClick = onNavClick
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = navIcon,
                    contentDescription = "Navigation Button"
                )
            }
        } else {
            Spacer(Modifier.size(iconSize))
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = title,
            fontSize = 20.sp
        )

        Spacer(Modifier.weight(1f))

        if (menuIcon != null) {
            IconButton(
                modifier = Modifier.size(iconSize),
                onClick = onMenuClick
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = menuIcon,
                    contentDescription = "Menu Button"
                )
            }
        } else {
            Spacer(Modifier.size(iconSize))
        }
    }
}