package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PerfumeView(imageUrl: String, width: Int, height: Int, backgroundColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        com.skydoves.landscapist.glide.GlideImage(
            imageModel = imageUrl,
            modifier = Modifier.width(width.dp).height(height.dp).background(color = backgroundColor),
        )
    }
}
