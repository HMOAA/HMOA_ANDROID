package com.hmoa.component

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun PostListItem(
    modifier : Modifier = Modifier,
    onPostClick : () -> Unit,
    postType : String,
    postTitle : String,
    heartCount : Int,
    commentCount : Int,
){
    Row(
        modifier = modifier
            .background(color = Color.White)
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
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = postType,
                    fontSize = 14.sp,
                    color = CustomColor.gray2,
                    style = TextStyle(fontWeight = FontWeight.Normal,fontFamily = pretendard)
                )

                Spacer(Modifier.weight(1f))

                if (heartCount > 0) {
                    CountBadge(
                        icon = R.drawable.ic_heart_filled,
                        count = heartCount
                    )
                }
                if (commentCount > 0) {
                    Spacer(Modifier.width(8.dp))
                    CountBadge(
                        icon = R.drawable.ic_kakao,
                        count = commentCount
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = postTitle,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontFamily = CustomFont.regular,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPostListItem(){
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        PostListItem (
            Modifier
                .fillMaxWidth()
                .height(52.dp)
                .border(width = 1.dp, color = CustomColor.gray2, shape = RoundedCornerShape(10.dp)),
            onPostClick = {},
            postType = "추천해주세요",
            postTitle = "여자친구한테 선물할 향수 뭐가 좋을까요?",
            heartCount = 10,
            commentCount = 10
        )
        Spacer(Modifier.height(10.dp))
        PostListItem (
            Modifier
                .fillMaxWidth()
                .height(52.dp)
                .border(width = 1.dp, color = CustomColor.gray2, shape = RoundedCornerShape(10.dp)),
            onPostClick = {},
            postType = "추천해주세요",
            postTitle = "여자친구한테 선물할 향수 뭐가 좋을까요?",
            heartCount = 10,
            commentCount = 0
        )

    }
}

@Composable
fun CountBadge(
    icon : Int,
    count : Int
){
    Row(
        modifier = Modifier
            .width(40.dp)
            .height(20.dp)
            .background(color = CustomColor.gray1, shape = RoundedCornerShape(size = 20.dp))
            .clip(RoundedCornerShape(size = 20.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Icon(
            modifier = Modifier.size(12.dp),
            painter = painterResource(icon),
            contentDescription = "Count Badge"
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = count.toString(),
            fontSize = 12.sp,
            color = Color.Black,
        )
    }
}