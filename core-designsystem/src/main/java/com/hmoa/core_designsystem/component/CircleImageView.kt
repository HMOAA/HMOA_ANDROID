package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun CircleImageView(imgUrl:String, width:Int, height:Int) {
    Box(
    modifier = Modifier.width(width.dp)
    .height(height.dp)
    .clip(CircleShape)
    .background(color = Color.Transparent)
    ) {
        com.skydoves.landscapist.glide.GlideImage(imageModel = imgUrl,contentScale = ContentScale.Crop,)
    }
}