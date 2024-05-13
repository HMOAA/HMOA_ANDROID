package com.hmoa.feature_magazine.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MagazineRoute(

){
    MagazineScreen()
}

@Composable
fun MagazineScreen(){

}

@Composable
private fun MagazineContent(

){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
    ){

    }
}