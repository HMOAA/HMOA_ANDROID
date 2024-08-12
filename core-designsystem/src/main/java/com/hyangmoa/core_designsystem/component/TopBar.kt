package com.hyangmoa.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyangmoa.core_designsystem.R
import com.hyangmoa.core_designsystem.theme.CustomColor

@Composable
fun TopBar(
    iconSize : Dp = 20.dp, //icon 크기
    navIcon : Painter ?= null, //navigation 버튼
    onNavClick : () -> Unit = {}, //navigation click 이벤트
    menuIcon : Painter ?= null, //menu 버튼
    onMenuClick : () -> Unit = {}, //menu click 이벤트,
    menuIconColor: Color = CustomColor.black,
    title : String, //메인 타이틀
    titleColor : Color = Color.Black, //타이틀 글 색
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
            Icon(
                modifier = Modifier.size(iconSize).clickable { onNavClick() },
                painter = navIcon,
                contentDescription = "Navigation Button"
            )
        } else {
            Spacer(Modifier.size(iconSize))
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = title,
            fontSize = 20.sp,
            color = titleColor
        )

        Spacer(Modifier.weight(1f))

        if (menuIcon != null) {
            Icon(
                modifier = Modifier.size(iconSize).clickable { onMenuClick() },
                painter = menuIcon,
                contentDescription = "Menu Button",
                tint = menuIconColor
            )
        } else {
            Spacer(Modifier.size(iconSize))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TopBarPreview(){
    TopBar(
        title = "댓글",
        iconSize = 25.dp,
        navIcon = painterResource(R.drawable.ic_back),
        onNavClick = {  },
        menuIcon = painterResource(R.drawable.three_dot_menu_horizontal),
        onMenuClick = {},
        menuIconColor = CustomColor.gray2
    )
}