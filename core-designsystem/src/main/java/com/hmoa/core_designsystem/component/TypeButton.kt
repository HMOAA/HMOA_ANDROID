package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor

//여기 Component가 자세히 나와 있지 않아서 일단 감으로 합니다
@Composable
fun TypeButton(
    height : Dp,
    roundedCorner : Dp,
    type : String,
    fontSize : TextUnit,
    fontColor : Color,
    selected : Boolean,
){
    val fontStyle = TextStyle(
        fontSize = fontSize,
        color = fontColor
    )
    val backgroundColor = if (selected) Color.Black else CustomColor.gray2

    Row(
        modifier = Modifier.height(height)
            .defaultMinSize(minWidth = 48.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(size = roundedCorner)),
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = type,
            style = fontStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestTypeButton(){
    TypeButton(
        height = 22.dp,
        roundedCorner = 20.dp,
        type = "추천",
        fontSize = 14.sp,
        fontColor = Color.White,
        selected = false
    )
}