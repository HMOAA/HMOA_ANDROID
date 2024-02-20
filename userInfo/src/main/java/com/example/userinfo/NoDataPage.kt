package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Data가 없는 화면
@Composable
fun NoDataPage(
    mainMsg : String,
    subMsg : String
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ){
        Spacer(Modifier.height(66.dp))

        Text(
            text = mainMsg,
            fontSize = 30.sp
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = subMsg,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun TestNoDataPage(){
    NoDataPage(
        mainMsg = "좋아요를 누른 댓글이\n없습니다",
        subMsg = "좋아하는 함수에 좋아요를 눌러주세요"
    )
}
