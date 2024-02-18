package com.hmoa.core_designsystem.component

import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R

@Composable
fun OAuthLoginBtn(
    backgroundColor : Color, //버튼의 background 색
    icon : Int, //icon의 id ex) R.id.kakao_img
    btnMsg : String, //버튼 메세지
    msgColor : Color, //버튼 메세지 글자 색
    onBtnClick : () -> Unit, //버튼 이벤트
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 16.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(size = 5.dp))
            .clickable {
                onBtnClick()
            }
            .padding(horizontal = 48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            modifier = Modifier.size(38.dp),
            painter = painterResource(icon),
            contentDescription = "OAuth Type Icon",
            tint = Color.White
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = btnMsg,
            fontSize = 16.sp,
            color = msgColor
        )

        Spacer(Modifier.weight(1f))

        Spacer(Modifier.size(38.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TestOAuthLoginBtn(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
//        OAuthLoginBtn(
//            backgroundColor = Color.Black,
//            icon = Icons.Filled.Person,
//            btnMsg = "Apple로 로그인하기",
//            msgColor = Color.White,
//            onBtnClick = {
//
//            }
//        )
    }
}