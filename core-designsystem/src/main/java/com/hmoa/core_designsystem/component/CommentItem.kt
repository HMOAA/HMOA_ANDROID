package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun CommentItem(
    count: Int,
    isCommentLiked: Boolean,
    userImgUrl: String,
    userName: String,
    content: String,
    createdDate: Int,
    onReportClick: () -> Unit,
    onCommentItemClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().height(102.dp).padding(10.dp).clickable { onCommentItemClick() }) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.width(28.dp)
                        .height(28.dp)
                        .clip(CircleShape)
                        .background(color = Color.Transparent)
                        .border(1.dp, Color.White, CircleShape)
                ) {
                    com.skydoves.landscapist.glide.GlideImage(imageModel = userImgUrl)
                }
                Text(
                    text = userName,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "${createdDate}일전",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = CustomColor.gray3),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Row {
                Icon(
                    painter = painterResource(com.hmoa.core_designsystem.R.drawable.ic_kebab),
                    contentDescription = "아이콘",
                    modifier = Modifier.size(12.dp).padding(2.dp).clickable { onReportClick() },
                    tint = CustomColor.gray2
                )
                TypeBadge(
                    roundedCorner = 20.dp,
                    type = "${count}",
                    fontColor = Color.Black,
                    unSelectedIcon = painterResource(R.drawable.ic_heart),
                    selectedIcon = painterResource(R.drawable.ic_heart_filled),
                    iconColor = Color.Black,
                    fontSize = TextUnit(value = 12f, type = TextUnitType.Sp),
                    selected = isCommentLiked,
                    unSelectedColor = CustomColor.gray1,
                    selectedColor = Color.Black
                )
            }
        }
        Text(
            content,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light),
            modifier = Modifier.padding(top = 8.dp),
            overflow = TextOverflow.Clip
        )
    }
}

@Preview
@Composable
fun CommentItemPreview() {
    CommentItem(
        300,
        false,
        userImgUrl = "",
        userName = "임현규",
        content = "선물용으로 구매했는데, 친구가 좋아했어요",
        createdDate = 12,
        onReportClick = {},
        onCommentItemClick = {})
}