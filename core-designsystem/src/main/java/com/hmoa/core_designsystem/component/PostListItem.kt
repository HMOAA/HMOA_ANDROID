package com.hmoa.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostListItem(
    onPostClick : () -> Unit,
    postType : String,
    postTitle : String,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .border(width = 1.dp, color = Color(0xFFBBBBBB))
            .clickable {
                onPostClick()
            }
            .padding(start = 32.dp),
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = postType,
                fontSize = 14.sp,
                color = Color(0xFFBBBBBB)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = postTitle,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPostListItem(){
    PostListItem (
        onPostClick = {},
        postType = "추천해주세요",
        postTitle = "여자친구한테 선물할 향수 뭐가 좋을까요?"
    )
}
