package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun TagBadge(
    height : Dp = 28.dp,
    backgroundColor : Color = Color.White,
    shape: Shape = RoundedCornerShape(14.dp),
    textColor : Color = CustomColor.gray3,
    borderColor: Color = CustomColor.gray3,
    tag : String,
    isClickable : Boolean = false,
    onClick : (String) -> Unit = {},
){
    Text(
        modifier = Modifier
            .wrapContentWidth()
            .height(height)
            .background(color = backgroundColor, shape = shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .then(
                if (isClickable) {
                    Modifier.clickable{onClick(tag)}
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
        text = tag,
        fontSize = 12.sp,
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        color = textColor,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun TestMagazineTag(){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TagBadge(tag = "#Test", isClickable = true)
    }
}