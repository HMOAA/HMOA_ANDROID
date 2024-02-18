package com.hmoa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LikeGridItem(
    itemPicture : ImageVector,
){
    Box(
        modifier = Modifier
            .size(104.dp)
            .border(width = 1.dp, color = Color(0xFFBBBBBB))
            .background(color = Color.White),
        contentAlignment = Alignment.BottomEnd
    ){
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = itemPicture,
            contentDescription = "Item Picture"
        )
        Box(
            modifier = Modifier.size(22.dp)
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

@Preview(showBackground = true)
@Composable
fun TestLikeGridItem(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        LikeGridItem(
            itemPicture = Icons.Filled.Delete
        )
    }
}