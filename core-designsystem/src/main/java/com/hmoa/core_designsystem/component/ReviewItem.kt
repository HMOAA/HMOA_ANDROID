package com.hmoa.core_designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_designsystem.theme.CustomFont

@Composable
fun ReviewItem(
    profileImg: String,
    nickname: String,
    writtenAt: String,
    isLiked: Boolean,
    heartNumber: Int,
    content: String,
    images: List<String>,
    category: String,
    onHeartClick: () -> Unit,
    onMenuClick: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = CustomColor.gray5, shape = RoundedCornerShape(size = 5.dp))
            .padding(top = 20.dp, start = 20.dp, end = 8.dp, bottom = 8.dp),
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            CircleImageView(
                imgUrl = profileImg,
                width = 28,
                height = 28
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = nickname,
                fontSize = 14.sp,
                color = CustomColor.gray1,
                fontFamily = CustomFont.regular
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = writtenAt,
                fontSize = 10.sp,
                color = CustomColor.gray3,
                fontFamily = CustomFont.medium
            )
            Spacer(Modifier.weight(1f))
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = onHeartClick
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(if(isLiked) R.drawable.ic_heart_selectable_selected else R.drawable.ic_heart_selectable_not_selected),
                    contentDescription = "Heart Icon"
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = heartNumber.toString(),
                fontSize = 14.sp,
                fontFamily = CustomFont.regular
            )
            IconButton(
                onClick = onMenuClick
            ){
                Icon(
                    painter = painterResource(R.drawable.three_dot_menu_vertical),
                    contentDescription = "Menu Button"
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = content,
            fontSize = 12.sp,
            fontFamily = CustomFont.regular,
            color = Color.White,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(20.dp))
        if (images.isNotEmpty()){Images(images = images)}
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = "향BTI 시향카드 [${category}]",
                fontSize = 9.sp,
                fontFamily = CustomFont.regular
            )
        }
    }
}

@Composable
private fun Images(images: List<String>){
    LazyRow(
        modifier = Modifier.padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        items(images){ image ->
            Box(
                modifier = Modifier.size(80.dp)
            ){
                ImageView(
                    imageUrl = image,
                    width = 1f,
                    height = 1f,
                    backgroundColor = CustomColor.gray6,
                    contentScale = ContentScale.Fit,
                    alpha = 1f
                )
            }
        }
    }
}

@Composable
@Preview
private fun ReviewItemUITest(){
    var isLiked by remember{mutableStateOf(false)}
    ReviewItem(
        profileImg = "",
        nickname = "향수 러버",
        writtenAt = "10일 전",
        isLiked = isLiked,
        heartNumber = 12,
        content = "평소에 선호하는 향이 있었는데 그 향의 이름을 몰랐는데 향료 배송받고 시향해보니 통카 빈?이더라구요 제가 좋아했던 향수들은 다 통카 빈이 들어가있네요 ㅎ 저같은 분들한테 추천합니다",
        images = listOf("","","","",""),
        category = "시트러스",
        onHeartClick = {isLiked = !isLiked},
        onMenuClick = {}
    )
}
