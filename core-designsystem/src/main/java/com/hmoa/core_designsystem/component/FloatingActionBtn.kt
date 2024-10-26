package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun FloatingActionBtn(
    navRecommend: () -> Unit,
    navHbtiReview: () -> Unit,
    navFree: () -> Unit,
    isAvailable: Boolean,
) {

    var isOpen by remember { mutableStateOf(false) }

    val textStyle = TextStyle(
        color = Color.White,
        fontSize = 16.sp,
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal
    )

    Column(
        modifier = Modifier.width(135.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .clickable { if (isAvailable) isOpen = !isOpen }
                    .background(color = Color.White, shape = CircleShape),
                painter = painterResource(if (isOpen) R.drawable.ic_fab_open else R.drawable.ic_fab),
                contentDescription = "FAB"
            )
        }

        Spacer(Modifier.height(8.dp))

        DropdownMenu(
            modifier = Modifier
                .height(138.dp)
                .width(135.dp)
                .background(color = Color.Black, shape = RoundedCornerShape(10.dp)),
            expanded = isOpen,
            onDismissRequest = {
                isOpen = false
            },
            offset = DpOffset(x = 0.dp, y = (-204).dp)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.height(46.dp),
                        text = "추천",
                        style = textStyle
                    )
                },
                onClick = {
                    isOpen = false
                    navRecommend()
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.height(46.dp),
                        text = "시향기",
                        style = textStyle
                    )
                },
                onClick = {
                    isOpen = false
                    navHbtiReview()
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.height(46.dp),
                        text = "자유",
                        style = textStyle
                    )
                },
                onClick = {
                    isOpen = false
                    navFree()
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TestFAB() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionBtn(
            navRecommend = {},
            navHbtiReview = {},
            navFree = {},
            isAvailable = true,
        )
    }
}