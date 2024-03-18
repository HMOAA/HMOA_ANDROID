package com.hmoa.core_designsystem.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.hmoa.core_designsystem.theme.CustomColor
import kotlin.math.roundToInt

@Composable
fun Slider(
    onSlideEnd : () -> Unit,
){

    var offsetX by remember{mutableStateOf(0f)}

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(color = CustomColor.gray1, shape = RoundedCornerShape(size = 5.dp))
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    offsetX += pan.x

                    val limitOffset = (296 - 52).dp.toPx()

                    offsetX = offsetX.coerceIn(0f, limitOffset)

                    if (offsetX >= limitOffset) {
                        // slide가 끝에 도달했을 때 이벤트
                        onSlideEnd()
                    }
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .size(52.dp)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .background(color = Color.Blue, shape = RoundedCornerShape(size = 5.dp)),
            contentAlignment = Alignment.Center
        ){

        }
        Box(
            modifier = Modifier
                .size(52.dp)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .background(color = CustomColor.gray2, shape = RoundedCornerShape(size = 5.dp)),
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
            color = CustomColor.gray2,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestSlider(){

    var test by remember{mutableStateOf("init")}

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Slider(
            onSlideEnd = {
                test = "reach the end"
            }
        )

        Column {
            Spacer(Modifier.height(90.dp))
            Text(
                text = test,
                fontSize = 30.sp
            )
        }
    }
}