package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//인증이 안되어 있는 My Page
@Composable
fun NoAuthMyPage(){
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ){
        Spacer(Modifier.height(126.dp))

    }
}

@Preview
@Composable
fun TestNoAuthMyPage(){
    NoAuthMyPage()
}