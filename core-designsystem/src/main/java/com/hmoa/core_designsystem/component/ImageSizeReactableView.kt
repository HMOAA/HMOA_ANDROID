package com.hmoa.core_designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ImageSizeReactableView(imageUrl: String?, width: Float, height: Float, backgroundColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        GlideImage(
            imageModel = imageUrl?: "",
            modifier = Modifier.fillMaxWidth(width).fillMaxHeight(height).background(color = backgroundColor),
        )
    }
}