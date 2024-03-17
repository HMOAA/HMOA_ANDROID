package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide

@Composable
fun ImageKebabView(imageUrl: String) {
    val width = LocalConfiguration.current.screenWidthDp.dp / 6
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        KebabFrame(width,imageUrl)
    }
}

@Composable
fun KebabFrame(size: Dp,imageUrl: String) {
    Row(modifier = Modifier.background(color = Color.Transparent)) {
        Box(
            modifier = Modifier.width(size)
                .height(size)
                .clip(CircleShape)
                .background(color = Color.Transparent)
                .border(1.dp, Color.White, CircleShape)
        ){
            com.skydoves.landscapist.glide.GlideImage(
                imageModel = imageUrl,
                modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp).height(size),
            )
        }
        Box(
            modifier = Modifier.width(size)
                .height(size)
                .clip(CircleShape)
                .background(color = Color.Transparent)
                .border(1.dp, Color.White, CircleShape)
        ){
            com.skydoves.landscapist.glide.GlideImage(
                imageModel = imageUrl,
                modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp).height(size),
            )
        }
        Box(
            modifier = Modifier.width(size)
                .height(size)
                .clip(CircleShape)
                .background(color = Color.Transparent)
                .border(1.dp, Color.White, CircleShape)
        ){
            com.skydoves.landscapist.glide.GlideImage(
                imageModel = imageUrl,
                modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp).height(size),
            )
        }
    }
}

@Composable
fun OverlappingBoxes(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val height = LocalConfiguration.current.screenWidthDp.dp / 6
    Layout(
        modifier = modifier.addModifier(Modifier.height(height)),
        content = content,
    ) { measurables, constraints ->
        val largeBox = measurables[0]
        val smallBox = measurables[1]
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val largePlaceable = largeBox.measure(looseConstraints)
        val smallPlaceable = smallBox.measure(looseConstraints)
        layout(
            width = constraints.maxWidth,
            height = largePlaceable.height + smallPlaceable.height / 2,
        ) {
            largePlaceable.placeRelative(
                x = 0,
                y = 0,
            )
            smallPlaceable.placeRelative(
                x = (constraints.maxWidth - smallPlaceable.width) / 2,
                y = largePlaceable.height - smallPlaceable.height / 2
            )
        }
    }
}


@Preview
@Composable
fun ImageKebabViewPreview() {
    ImageKebabView("https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg")
}