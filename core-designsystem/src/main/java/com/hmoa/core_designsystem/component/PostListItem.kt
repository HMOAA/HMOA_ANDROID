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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun PostListItem(
    onPostClick : () -> Unit,
    postType : String,
    postTitle : String,
    heartCount : String,
    isLiked : Boolean = false,
    commentCount : String,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(width = 1.dp, color = CustomColor.gray2, shape = RoundedCornerShape(10.dp))
            .clickable {
                onPostClick()
            }
            .padding(start = 16.dp, end = 12.dp)
            .padding(vertical = 16.dp),
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = postType,
                    fontSize = 14.sp,
                    color = CustomColor.gray2
                )

                Spacer(Modifier.weight(1f))

                TypeBadge(
                    roundedCorner = 20.dp,
                    type = heartCount,
                    fontSize = 12.sp,
                    fontColor = Color.Black,
                    selected = isLiked,
                    icon = painterResource(R.drawable.heart_icon),
                )

                Spacer(Modifier.width(8.dp))

                TypeBadge(
                    roundedCorner = 20.dp,
                    type = commentCount,
                    fontSize = 12.sp,
                    fontColor = Color.Black,
                    selected = isLiked,
                    //여기는 Comment Icon으로 바꿔야 함
                    icon = painterResource(R.drawable.heart_icon),
                    iconColor = Color.Black,
                )
            }

            Spacer(Modifier.height(8.dp))

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
        postTitle = "여자친구한테 선물할 향수 뭐가 좋을까요?",
        heartCount = "10",
        isLiked = false,
        commentCount = "18"
    )
}
