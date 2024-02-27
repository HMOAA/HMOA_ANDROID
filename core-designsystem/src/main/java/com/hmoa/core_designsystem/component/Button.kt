package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Button(
    isEnabled : Boolean, //버튼 활성화 여부
    btnText : String, //버튼 메세지
    onClick : () -> Unit, //버튼 이벤트
    buttonModifier: Modifier? = null,
    textColor: Color = Color.White,
    textSize:Int = 20,
    radious:Int = 0
){
    Row(
        modifier = Modifier
            .background(color = if(isEnabled) Color.Black else Color(0xFFBBBBBB), shape = RoundedCornerShape(radious))
            .clickable{
                if (isEnabled){
                    onClick()
                }
            }.addModifier(buttonModifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = btnText,
            fontSize = textSize.sp,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestBottomButton(){

    var text = "init"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){
        Button(
            isEnabled = false,
            btnText = "다음",
            onClick = {
                text = "btn clicked"
            }
        )

        Spacer(Modifier.height(50.dp))

        Text(
            text = text,
            fontSize = 30.sp
        )
    }
}