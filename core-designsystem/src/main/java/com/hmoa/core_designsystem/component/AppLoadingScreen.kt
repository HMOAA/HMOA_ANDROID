package com.hmoa.core_designsystem.component

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun AppLoadingScreen(){
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components{
            if(Build.VERSION.SDK_INT >= 28){
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    val painter = rememberAsyncImagePainter(
        model = R.drawable.loading,
        imageLoader = imageLoader
    )
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = CustomColor.gray10),
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Loading GIF"
        )
    }
}

@Preview
@Composable
fun TestLoading(){
    AppLoadingScreen()
}