package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.hmoa.core_designsystem.R

@Composable
fun TastingNote(
    painter : Painter
){
    Image(
        painter = painter,
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
fun TestTastingNote(){

}