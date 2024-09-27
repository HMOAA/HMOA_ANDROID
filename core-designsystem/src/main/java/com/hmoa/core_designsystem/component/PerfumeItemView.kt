package com.hmoa.core_designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.theme.CustomFont

@Composable
fun PerfumeItemView(
    imageUrl: String,
    perfumeName: String,
    brandName: String,
    containerWidth: Int,
    containerHeight: Int,
    imageWidth: Float,
    imageHeight: Float,
    imageBackgroundColor: Color,
    imageBorderStroke: BorderStroke?,
) {
    Column(modifier = Modifier.padding(end = 8.dp).width(containerWidth.dp)) {
        Column(
            modifier = Modifier.width(containerWidth.dp).height(containerHeight.dp)
                .background(imageBackgroundColor).border(border = imageBorderStroke?: BorderStroke(width = 0.dp, color = Color.Transparent)),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageView(
                imageUrl = imageUrl,
                backgroundColor = imageBackgroundColor,
                width = imageWidth,
                height = imageHeight,
                contentScale = ContentScale.Fit
            )
        }
        Text(
            text = brandName, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal,
                fontFamily = CustomFont.regular
            ),
            modifier = Modifier.padding(end = 4.dp, top = 8.dp, bottom = 6.dp)
        )
        Text(
            text = perfumeName, style = TextStyle(fontSize = 12.sp,fontWeight = FontWeight.Normal,
                fontFamily = CustomFont.regular),
            modifier = Modifier.padding(end = 4.dp), softWrap = true
        )
    }
}