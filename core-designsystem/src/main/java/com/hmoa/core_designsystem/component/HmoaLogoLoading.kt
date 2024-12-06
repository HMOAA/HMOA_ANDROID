package com.hmoa.core_designsystem.component

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.hmoa.core_designsystem.R

@Composable
fun HmoaLogoLoading() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    val painter = rememberAsyncImagePainter(
        model = R.drawable.hmoa,
        imageLoader = imageLoader
    )
    Box(
        modifier = Modifier
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.width(230.dp).height(230.dp),
            painter = painter,
            contentDescription = "Hmoa logo gif"
        )
    }
}

@Preview
@Composable
fun HmoaLogoLoadingPreview() {
    HmoaLogoLoading()
}