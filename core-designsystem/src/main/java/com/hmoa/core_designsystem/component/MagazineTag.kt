package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun MagazineTag(
    tag : String
){
    Text(
        modifier = Modifier
            .wrapContentWidth()
            .height(28.dp)
            .background(color = Color.White, shape = RoundedCornerShape(14.dp))
            .border(width = 1.dp, color = CustomColor.gray3, shape = RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        text = tag,
        fontSize = 12.sp,
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        color = CustomColor.gray3,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun TestMagazineTag(){
    Row(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ){
        MagazineTag(tag = "#Test")
    }
}