package com.hyangmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyangmoa.core_designsystem.R
import com.hyangmoa.core_designsystem.theme.CustomColor

@Composable
fun VoteView(
    percentage: Int,
    icon: Painter,
    title: String,
    onVote: () -> Unit
) {
    val percent = percentage * 0.01
    val textColor = if (percentage > 86) Color.White else Color.Black
    val iconColor = if (percentage > 45) Color.White else CustomColor.gray2
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(52.dp)
                .height(80.dp)
                .background(color = CustomColor.gray1, shape = RoundedCornerShape(size = 5.dp))
                .clickable { onVote() },
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
                    .background(color = Color.Transparent),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((80 * percent).dp)
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(
                                bottomStart = 5.dp,
                                bottomEnd = 5.dp,
                                topStart = 5.dp,
                                topEnd = 5.dp
                            )
                        )
                )
            }
            Icon(
                modifier = Modifier.size(16.dp),
                painter = icon,
                contentDescription = "Main Image",
                tint = iconColor
            )
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(color = Color.Transparent),
                contentAlignment = Alignment.TopCenter,
            ) {
                Spacer(Modifier.height(7.dp))
                Text(
                    modifier = Modifier.height(12.dp),
                    text = "${percentage}%",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
            }
        }
        Text(
            title,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
            modifier = Modifier.padding(vertical = 9.dp)
        )
    }
}

@Preview
@Composable
fun VoteViewPreview() {
    VoteView(89, icon = painterResource(R.drawable.ic_winter), title = "겨울", {})
}