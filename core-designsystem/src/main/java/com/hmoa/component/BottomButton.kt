package com.hmoa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomButton(
    isEnabled : Boolean, //버튼 활성화 여부
    btnText : String, //버튼 메세지
    onClick : () -> Unit, //버튼 이벤트
){
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(80.dp)
            .background(color = if(isEnabled) Color.Black else Color(0xFFBBBBBB))
            .clickable{
                if (isEnabled){
                    onClick()
                }
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = btnText,
            fontSize = 20.sp,
            color = Color.White
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
        BottomButton(
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