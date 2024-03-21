package com.hmoa.feature_perfume

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.component.TopBar
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.component.CircleImageView
import com.hmoa.core_designsystem.component.TypeBadge
import com.hmoa.core_designsystem.theme.CustomColor
import com.hmoa.core_model.response.PerfumeCommentResponseDto

@Composable
fun SpecificCommentRoute(onBackClick: () -> Unit) {
    SpecificCommentScreen(onBackClick = { onBackClick() })
}

@Composable
fun SpecificCommentScreen(onBackClick: () -> Unit) {

}

@Composable
fun SpecificCommentContent(onBackClick: () -> Unit, data: PerfumeCommentResponseDto) {
    Column {
        TopBar(
            title = "댓글",
            iconSize = 25.dp,
            navIcon = painterResource(R.drawable.ic_back),
            onNavClick = { onBackClick() },
        )
        ProfileAndHeartView(
            count = data.heartCount,
            isCommentLiked = data.liked,
            userImgUrl = data.profileImg,
            userName = data.nickname,
            content = data.content,
            createdDate = data.createdAt.toInt(),
            onReportClick = {}
        )
    }
}

@Composable
fun ProfileAndHeartView(
    count: Int,
    isCommentLiked: Boolean,
    userImgUrl: String,
    userName: String,
    content: String,
    createdDate: Int,
    onReportClick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 9.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                CircleImageView(userImgUrl, 28, 28)
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
                    painter = painterResource(R.drawable.ic_kebab),
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
            text = content,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light, color = CustomColor.gray3),

            )
    }
}

@Preview
@Composable
fun SpecificCommentPreview() {
    ProfileAndHeartView(
        count = 10,
        isCommentLiked = false,
        userName = "이용인",
        userImgUrl = "",
        content = "기존에 사용하던 향이라 재구매했어요. 계절에 상관없이 사용할 수 있어서 좋아요",
        createdDate = 10,
        onReportClick = {}
    )
}