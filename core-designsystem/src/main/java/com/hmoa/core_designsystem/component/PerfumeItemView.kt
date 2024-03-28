package com.hmoa.core_designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerfumeItemView(imageUrl: String, perfumeName: String, brandName: String, width: Int, height: Int, imageBackgroundColor:Color) {
    Column(modifier = Modifier.padding(end = 8.dp).width(88.dp)) {
        ImageView(imageUrl = imageUrl, backgroundColor = imageBackgroundColor, width = width, height = height)
        Text(
            text = brandName, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = perfumeName, style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(end = 4.dp), softWrap = true
        )
    }
}