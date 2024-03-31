package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerfumeItemView(
    imageUrl: String,
    perfumeName: String,
    brandName: String,
    containerWidth: Int,
    containerHeight: Int,
    imageWidth: Float,
    imageHeight: Float,
    imageBackgroundColor: Color
) {
    Column(modifier = Modifier.padding(end = 8.dp).width(containerWidth.dp)) {
        Column(
            modifier = Modifier.width(containerWidth.dp).height(containerHeight.dp)
                .background(imageBackgroundColor),
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
            text = brandName, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(end = 4.dp, top = 8.dp)
        )
        Text(
            text = perfumeName, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(end = 4.dp), softWrap = true
        )
    }
}