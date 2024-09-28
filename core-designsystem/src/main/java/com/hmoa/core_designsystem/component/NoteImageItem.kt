package com.hmoa.core_designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.pretendard
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun NoteImageView(
    imageUrl: String?,
    width: Float,
    height: Float,
    backgroundColor: Color,
    contentScale: ContentScale,
    alpha: Float = 1f,
    onClicked: () -> Unit,
    isRecommanded: Boolean,
    index: Int? = null,
    isSelected: Boolean
) {
    val index = if (isRecommanded) "Best!" else index
    val nonSelectText = if (isRecommanded) "Best!" else ""
    val itemBorderColor = if (isSelected) CustomColor.black else Color.Transparent
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clickable { onClicked() }.background(color = Color.Transparent, shape = CircleShape)
            .border(shape = CircleShape, border = BorderStroke(width = 2.dp, color = itemBorderColor))
            .height(height.dp).width(width.dp).clip(CircleShape)
    ) {
        GlideImage(
            imageModel = imageUrl ?: "",
            modifier = Modifier.fillMaxWidth(width).fillMaxHeight(height).background(color = backgroundColor),
            contentScale = contentScale,
            alpha = alpha
        )
        if (isSelected) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.background(color = CustomColor.blackTrans70, shape = CircleShape).padding(0.dp)
                        .height((height - 9).dp)
                        .width((width - 9).dp)
                        .border(shape = CircleShape, border = BorderStroke(width = 1.dp, color = Color.Transparent))
                ) {
                    Text(
                        "${index ?: ""}",
                        style = TextStyle(color = Color.White, fontFamily = pretendard, fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
        else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    "${nonSelectText}",
                    style = TextStyle(color = Color.Black, fontFamily = pretendard, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Preview
@Composable
fun NoteImageViewPreview() {
    Column {
        //선택 안함
        NoteImageView("", 74f, 74f, Color.Blue, ContentScale.FillWidth, 1f, {}, false, 0, false)
        //선택 안함, 추천된 노드
        NoteImageView("", 74f, 74f, Color.Blue, ContentScale.FillWidth, 1f, {}, true, 1, false)
        //선택함, 추천된 노트
        NoteImageView("", 74f, 74f, Color.Transparent, ContentScale.FillWidth, 1f, {}, true, 2, true)
        //선택함
        NoteImageView("", 74f, 74f, Color.Transparent, ContentScale.FillWidth, 1f, {}, false, 3, true)
    }
}