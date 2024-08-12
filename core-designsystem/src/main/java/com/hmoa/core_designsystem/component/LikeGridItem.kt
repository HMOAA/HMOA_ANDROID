package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun LikeGridItem(
    itemPicture : String,
    onClickItem : () -> Unit,
){
    Box(
        modifier = Modifier
            .size(104.dp)
            .border(width = 1.dp, color = Color(0xFFBBBBBB))
            .background(color = Color.White)
            .clickable{onClickItem()},
        contentAlignment = Alignment.BottomEnd
    ){
        ImageView(
            imageUrl = itemPicture,
            width = 1f,
            height = 1f,
            backgroundColor = Color.White,
            contentScale = ContentScale.Fit
        )
        Box(
            modifier = Modifier
                .size(22.dp)
                .padding(end = 8.dp, bottom = 8.dp)
        ){
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite Icon"
            )
        }
    }
}