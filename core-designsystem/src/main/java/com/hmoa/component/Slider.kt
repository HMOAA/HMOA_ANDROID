package com.hmoa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import kotlin.math.roundToInt

@Composable
fun Slider(){

    val offsetX by remember{mutableStateOf(0f)}
    val offsetY by remember{mutableStateOf(0f)}

    Row(
        modifier = Modifier
            .width(296.dp)
            .height(52.dp)
            .background(color = Color(0xFFF4F4F4), shape = RoundedCornerShape(size = 5.dp)),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(color = Color(0xFFBBBBBB), shape = RoundedCornerShape(size = 5.dp))
                .offset{ IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) },
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Sliding Icon",
                tint = Color.White
            )
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = "드래그 해주세요",
            fontSize = 16.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight(400),
            color = Color(0xFFBBBBBB),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestSlider(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Slider()
    }
}