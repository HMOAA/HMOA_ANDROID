package com.hmoa.core_designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmoa.core_designsystem.R
import com.hmoa.core_designsystem.theme.pretendard

@Composable
fun FloatingActionBtn(
    width: Dp,
    fontSize: TextUnit,
    options: List<String>,
    events: List<() -> Unit>,
    isAvailable: Boolean,
) {
    var isOpen by remember { mutableStateOf(false) }
    val itemHeight = when(fontSize){
        16.sp -> 40
        12.sp -> 35
        else -> 30
    }
    val height = options.size * itemHeight
    val textStyle = TextStyle(
        color = Color.White,
        fontSize = fontSize,
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal
    )

    Column(
        modifier = Modifier.width(width)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
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
                .wrapContentHeight()
                .width(width)
                .background(color = Color.Black),
            expanded = isOpen,
            onDismissRequest = {
                isOpen = false
            },
            offset = DpOffset(x = 0.dp, y = (-(90 + height)).dp)
        ) {
            repeat(options.size){ idx ->
                Row(
                    modifier = Modifier.height(itemHeight.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = options[idx],
                                style = textStyle,
                                textAlign = TextAlign.Center
                            )
                        },
                        onClick = {
                            isOpen = false
                            events[idx]
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestFAB() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionBtn(
            options = listOf("추천", "시향기", "자유"),
//            options = listOf("후기 작성하기 (시트러스 24.10.08)"),
//            options = listOf("추천", "시향기"),
            events = listOf(),
            isAvailable = true,
            width = 135.dp,
            fontSize = 16.sp
        )
    }
}