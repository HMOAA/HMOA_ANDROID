package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

@Composable
fun ImageView(imageUrl: String?, width: Float, height: Float, backgroundColor: Color, contentScale: ContentScale) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        com.skydoves.landscapist.glide.GlideImage(
            imageModel = imageUrl ?: "",
            modifier = Modifier.fillMaxWidth(width).fillMaxHeight(height).background(color = backgroundColor),
            contentScale = contentScale
        )
    }
}

