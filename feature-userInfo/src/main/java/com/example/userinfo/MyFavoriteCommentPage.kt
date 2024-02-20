package com.example.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyFavoriteCommentPage(){
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.White)
    ){
        
    }
}

@Preview(showBackground = true)
@Composable
fun TestMyFavoriteCommentPage(){
    MyFavoriteCommentPage()
}