package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.CustomColor

@Composable
fun Comment(
    isEditable: Boolean,
    profile: String,
    nickname: String,
    dateDiff: String,
    comment: String,
    isFirst: Boolean,
    isSelected: Boolean,
    onChangeSelect: () -> Unit,
    heartCount: Int,
    onOpenBottomDialog: () -> Unit,
    navCommunity: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable {
                navCommunity()
            }
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Spacer(Modifier.height(11.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleImageView(imgUrl = profile, width = 28, height = 28)
            Spacer(Modifier.width(6.dp))
            Text(
                text = nickname,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight(400),
                color = Color.Black
            )
            Spacer(Modifier.width(7.dp))
            if (isFirst) {
                Spacer(Modifier.width(2.dp))
                Icon(
                    modifier = Modifier
                        .size(12.dp),
                    painter = painterResource(R.drawable.badge),
                    contentDescription = "Bedge",
                    tint = CustomColor.blue
                )
                Spacer(Modifier.width(5.dp))
            }
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = dateDiff,
                fontSize = 12.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight(300),
                color = CustomColor.gray3
            )
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditable) {
                    IconButton(
                        modifier = Modifier.size(16.dp),
                        onClick = onChangeSelect
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(1f),
                            painter = painterResource(R.drawable.ic_heart_selectable_not_selected),
                            tint = if (isSelected) CustomColor.red else CustomColor.gray2,
                            contentDescription = "Comment Like Button"
                        )
                    }
                } else {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(if (isSelected) R.drawable.ic_heart_selectable_selected else R.drawable.ic_heart_selectable_not_selected),
                        tint = if (isSelected) CustomColor.red else Color.Black,
                        contentDescription = "Comment Like"
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = if (heartCount <= 999) heartCount.toString() else "999+",
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }
            if (isEditable) {
                Spacer(Modifier.width(8.dp))

                IconButton(
                    onClick = onOpenBottomDialog
                ) {
                    Icon(
                        painter = painterResource(R.drawable.three_dot_menu_horizontal),
                        contentDescription = "Bottom Dialog Status Controller"
                    )
                }
            }
        }

        Spacer(Modifier.height(11.dp))

        Text(
            text = comment,
            fontSize = 14.sp,
            lineHeight = 19.6.sp,
            fontWeight = FontWeight(300),
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestComment() {
    Comment(
        isEditable = true,
        profile = "",
        nickname = "nickname",
        dateDiff = "2일 전",
        comment = "아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ",
        isFirst = true,
        isSelected = true,
        onChangeSelect = {},
        heartCount = 10,
        onOpenBottomDialog = {},
        navCommunity = {},
    )
}