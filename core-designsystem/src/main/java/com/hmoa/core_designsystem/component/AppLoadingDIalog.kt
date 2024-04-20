package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import kotlinx.coroutines.delay

@Composable
fun AppLoadingScreen(){

    var i by remember{mutableIntStateOf(0)}
    val loadingPainters = listOf(
        painterResource(R.drawable.ic_loading_1),
        painterResource(R.drawable.ic_loading_2),
        painterResource(R.drawable.ic_loading_3)
    )

    var currentPainter by remember{mutableStateOf(loadingPainters[0])}

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = CustomColor.gray10),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.size(56.dp)
                .background(color = Color.White, shape = CircleShape)
                .clip(CircleShape),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = currentPainter,
                contentDescription = "Loading Component"
            )
        }
    }

    LaunchedEffect(Unit){
        while(true){
            delay(300)
            i+=1
            currentPainter = loadingPainters[i % 3]
        }
    }
}

@Preview
@Composable
fun TestLoading(){
    AppLoadingScreen()
}