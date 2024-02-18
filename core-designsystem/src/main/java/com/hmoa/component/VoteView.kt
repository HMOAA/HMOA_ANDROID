package com.hmoa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VoteView(
    percentage : Int,
    icon : ImageVector
){
    val percent = percentage * 0.01

    Box(
        modifier = Modifier
            .width(52.dp)
            .height(80.dp)
            .background(color = Color(0xFFF4F4F4), shape = RoundedCornerShape(size = 5.dp)),
        contentAlignment = Alignment.Center
    ){

        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Color.Transparent),
            contentAlignment = Alignment.BottomCenter
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((80 * percent).dp)
                    .background(color = Color.Black)
            )
        }
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = icon,
            contentDescription = "Main Image",
            tint = Color(0xFFBBBBBB)
        )
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Color.Transparent),
            contentAlignment = Alignment.TopCenter
        ){
            Spacer(Modifier.height(5.dp))

            Text(
                modifier = Modifier.height(12.dp),
                text = "${percentage}%",
                fontSize = 10.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun testVoteView(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ){
        VoteView(
            percentage = 50,
            icon = Icons.Filled.Build
        )
    }
}